package ZIP.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class CompressObj implements Serializable {
	private static final long serialVersionUID = 6254234405289677195L;
	private String srcFile;// 資料來源
	private String destFile;// 資料目地
	private List<String> filterData;// 過濾資料

	public CompressObj() {
		super();
	}

	public String getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}

	public String getDestFile() {
		return destFile;
	}

	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}

	public List<String> getFilterData() {
		return filterData;
	}

	public void setFilterData(List<String> filterData) {
		this.filterData = filterData;
	}

	@Override
	public String toString() {
		return "CompressObj [srcFile=" + srcFile + ", destFile=" + destFile + ", filterData=" + filterData + "]";
	}

}
