package com.example.sangjin_lee.rentplattform;

/**
 * Created by Sangjin-Lee on 2017-12-13.
 */

public class report {
    private String reportTitle;    //  신고 제목
    private String reportContent; // 신고 내용
    private String userId;    // 신고한사람 이름(User로받으면 가격까지받아서 그냥 User에서 가져오기)
    private boolean checkReport;   // 신고 확인 체크
    String Date;

    public report() {

    }
    public report(String reportTitle,String reportContent, String userId, boolean checkReport, String Date) {
        this.reportTitle= reportTitle;
        this.reportContent = reportContent;
        this.userId= userId;
        this.checkReport = checkReport;
        this.Date = Date;
    }

    public String getreportTitle(){return reportTitle;}
    public String getreportContent(){return reportContent;}
    public String getuserId(){return userId;}
    public boolean getcheckReport() {return checkReport;}
    public String getDate() {return Date;}

    public void setreportTitle(String reportTitle) {this.reportTitle= reportTitle;}
    public void setreportContent(String reportContent) {this.reportContent= reportContent;}
    public void setuserId(String userId) {this.userId= userId;}
    public void setcheckReport(boolean checkReport) {this.checkReport= checkReport;}
    public void setDate(String Date) {this.Date = Date;}
}
