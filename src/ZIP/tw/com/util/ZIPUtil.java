package ZIP.tw.com.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import ZIP.tw.com.bean.CompressObj;
import ZIP.tw.com.bean.CompressObjList;

public class ZIPUtil {
	private static final int bufferSize = 4096;
	private static List<String> filterData;
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	private ZIPUtil() {
	}

	public static List<String> getFilterData() {
		return filterData;
	}

	public static void setFilterData(List<String> filterData) {
		ZIPUtil.filterData = filterData;
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

	public static void zip(String srcZIPFile, String destZIPFile) throws Exception {
		// 指定壓縮完成後zip檔案的儲存路徑
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZIPFile));

		// 獲取來源目錄下的所有檔案
		File[] files = new File(srcZIPFile).listFiles();
		for (String item : filterData) {
			if (!new File(item).exists()) {
				zos.close();
				new File(destZIPFile).delete();
				throw new IOException(String.format("過濾路徑%s不存在!", item));
			}
		}

		// 遍歷目錄下的所有檔案/目錄，並將它們添加到壓縮檔案中
		for (File file : files) {
			// 一個ZipEntry對應壓縮檔案中的一項
			if (file.isDirectory()) {
				if (!accept(filterData, new File(file.getAbsolutePath())))
					zipDirectory(file, file.getName(), zos);
			} else {
				if (!accept(filterData, new File(file.getAbsolutePath())))
					zipFile(file, zos);
			}
		}

		zos.flush();
		zos.close();
	}

	public static List<CompressObj> getCompressObjList() throws IOException {
		InputStream is = null;
		File jsonFile = new File("./CompressObjList.json");
		
		if (jsonFile.exists())
			is = new FileInputStream(jsonFile);
		else
			is = ZIPUtil.class.getClassLoader().getResourceAsStream("ZIP/CompressObjList.json");

		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		// 透過gson對json文件讀取
		Gson gson = new Gson();
		CompressObjList compressObjList = gson.fromJson(br, CompressObjList.class);
		br.close();

		return compressObjList.getCompressObjList();
	}

	/**
	 * 過濾
	 * 
	 * @param pathname
	 * @param srcFile
	 * @return
	 * @throws Exception
	 */
	public static boolean accept(List<String> filterData, File srcFile) throws Exception {
		boolean rs = false;

		for (int i = 0; i < filterData.size(); i++) {
			logger.debug(srcFile.getAbsolutePath());
			rs |= srcFile.getAbsolutePath().contains(filterData.get(i));
		}

		return rs;
	}

}
