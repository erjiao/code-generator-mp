package com.llk.generator.bean;

/**
 * @author llk
 * @date 2019-10-02 02:30
 */
public class GenConfig {

    private String dsType;

    private String url;

    private String username;

    private String password;

    private String table;

    private String tablePrefix;

    private String packageName;

    private String author;

    private String javaOutputDir;

    private String xmlOutputDir;

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getJavaOutputDir() {
        return javaOutputDir;
    }

    public void setJavaOutputDir(String javaOutputDir) {
        this.javaOutputDir = javaOutputDir;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getXmlOutputDir() {
        return xmlOutputDir;
    }

    public void setXmlOutputDir(String xmlOutputDir) {
        this.xmlOutputDir = xmlOutputDir;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "GenConfig{" +
                "dsType='" + dsType + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", table='" + table + '\'' +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", packageName='" + packageName + '\'' +
                ", author='" + author + '\'' +
                ", javaOutputDir='" + javaOutputDir + '\'' +
                ", xmlOutputDir='" + xmlOutputDir + '\'' +
                '}';
    }
}
