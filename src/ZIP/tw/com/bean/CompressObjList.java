package ZIP.tw.com.bean;

import java.io.Serializable;
import java.util.List;

public class CompressObjList implements Serializable {
	private static final long serialVersionUID = 9139623716027239439L;
	private List<CompressObj> compressObjList;

	public CompressObjList() {
		super();
	}

	public List<CompressObj> getCompressObjList() {
		return compressObjList;
	}

	public void setCompressObjList(List<CompressObj> compressObjList) {
		this.compressObjList = compressObjList;
	}

	@Override
	public String toString() {
		return "CompressObjList [compressObjList=" + compressObjList + "]";
	}
}
