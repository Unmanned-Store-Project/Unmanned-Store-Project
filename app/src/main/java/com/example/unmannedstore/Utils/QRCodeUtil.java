package com.wefour.UnmannedStore.Utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class QRCodeUtil {
	@Nullable
	public static Bitmap createQRCodeBitmap(@Nullable String content, int size, @ColorInt int color_black, @ColorInt int color_white){
		return createQRCodeBitmap(content, size, "UTF-8", "H", "4", color_black, color_white, null, null, 0F);
	}

	@Nullable
	public static Bitmap createQRCodeBitmap(@Nullable String content, int size,
                                            @Nullable String character_set, @Nullable String error_correction, @Nullable String margin,
                                            @ColorInt int color_black, @ColorInt int color_white, @Nullable Bitmap targetBitmap,
                                            @Nullable Bitmap logoBitmap, float logoPercent){

		/** 1.参数合法性判断 */
		if(TextUtils.isEmpty(content)){ // 字符串内容判空
			return null;
		}

		if(size <= 0){ // 宽&高都需要>0
			return null;
		}

		try {
			/** 2.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
			Hashtable<EncodeHintType, String> hints = new Hashtable<>();

			if(!TextUtils.isEmpty(character_set)) {
				hints.put(EncodeHintType.CHARACTER_SET, character_set); // 字符转码格式设置
			}

			if(!TextUtils.isEmpty(error_correction)){
				hints.put(EncodeHintType.ERROR_CORRECTION, error_correction); // 容错级别设置
			}

			if(!TextUtils.isEmpty(margin)){
				hints.put(EncodeHintType.MARGIN, margin); // 空白边距设置
			}
			BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

			/** 3.根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
			if(targetBitmap != null){
				targetBitmap = Bitmap.createScaledBitmap(targetBitmap, size, size, false);
			}
			int[] pixels = new int[size * size];
			for(int y = 0; y < size; y++){
				for(int x = 0; x < size; x++){
					if(bitMatrix.get(x, y)){ // 黑色色块像素设置
						if(targetBitmap != null) {
							pixels[y * size + x] = targetBitmap.getPixel(x, y);
						} else {
							pixels[y * size + x] = color_black;
						}
					} else { // 白色色块像素设置
						pixels[y * size + x] = color_white;
					}
				}
			}

			/** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象 */
			Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}

		return null;
	}
}
