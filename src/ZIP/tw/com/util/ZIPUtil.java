package ZIP.tw.com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZIPUtil {
	private static final int bufferSize = 4096;

	private ZIPUtil() {
	}

	public static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos)
			throws FileNotFoundException, IOException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				zipDirectory(file, parentFolder + "/" + file.getName(), zos);
				continue;
			}

			zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

			byte[] buff = new byte[bufferSize];
			int len = 0;
			while ((len = bis.read(buff)) != -1)
				zos.write(buff, 0, len);

			zos.closeEntry();
		}
	}

	public static void zipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {
		zos.putNextEntry(new ZipEntry(file.getName()));
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		byte[] buff = new byte[bufferSize];
		int len = 0;
		while ((len = bis.read(buff)) != -1)
			zos.write(buff, 0, len);

		zos.closeEntry();
	}

	public static void zip(String srcZIPFile, String destZIPFile) throws FileNotFoundException, IOException {
		// 指定壓縮完成後zip檔案的儲存路徑
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZIPFile));

		// 獲取來源目錄下的所有檔案
		File[] files = new File(srcZIPFile).listFiles();

		// 遍歷目錄下的所有檔案/目錄，並將它們添加到壓縮檔案中
		for (File file : files) {
			// 一個ZipEntry對應壓縮檔案中的一項
			if (file.isDirectory()) {
				zipDirectory(file, file.getName(), zos);
			} else {
				zipFile(file, zos);
			}
		}

		zos.flush();
		zos.close();
	}
}
