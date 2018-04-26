package zxn.util;

import java.util.List;

public class Page {
	private List records;
	private int pagenum;//��ǰҳ��
	private int pagesize=4;//ÿҳ��ʾ������
	private int totalrecords;//�ܼ�¼��
	private int totalpage;//��ҳ��
	private int startindex;//ÿҳ��ʼ������
	
	private String url;
	
	public Page(int pagenum,int totalrecords){
		this.pagenum = pagenum;
		this.totalrecords = totalrecords;
		//��ҳ��
		totalpage = (totalrecords%pagesize)==0?(totalrecords/pagesize):(totalrecords/pagesize+1);
		//����ÿҳ��ʼ������
		startindex = (pagenum-1)*pagesize;
	}
	public List getRecords() {
		return records;
	}
	public void setRecords(List records) {
		this.records = records;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalrecords() {
		return totalrecords;
	}
	public void setTotalrecords(int totalrecords) {
		this.totalrecords = totalrecords;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getStartindex() {
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
