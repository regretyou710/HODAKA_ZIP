package ZIP;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ZIP.tw.com.bean.CompressObj;
import ZIP.tw.com.util.ZIPUtil;

public class ZIPExe {
	private static Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");

	public static void main(String[] args) {
		try {
			List<CompressObj> compressObjList = ZIPUtil.getCompressObjList();

			for (CompressObj item : compressObjList) {
				double startTime = System.currentTimeMillis();
				ZIPUtil.setFilterData(item.getFilterData());

				File srcFile = new File(item.getSrcFile());
				if (!srcFile.exists())
					throw new IOException("來源路徑不存在!");

				File destFile = new File(item.getDestFile());

				ZIPUtil.zip(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
				double endTime = System.currentTimeMillis();
				logger.info("{}壓縮檔案成功!花費時間{}秒", item.getSrcFile(), (endTime - startTime) / 1000);

			}

		} catch (Exception e) {
			logger.error("出現異常:", e);
			// e.printStackTrace();
		}
	}
}
