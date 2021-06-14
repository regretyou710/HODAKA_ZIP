package ZIP;

import ZIP.tw.com.util.ZIPUtil;

public class ZIPExe {
	public static void main(String[] args) {
		try {
			ZIPUtil.zip("D:\\test", "G:\\test.zip");
		} catch (Exception e) {

			// e.printStackTrace();
		}
	}
}
