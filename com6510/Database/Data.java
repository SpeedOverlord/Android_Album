package uk.ac.shef.oak.com6510.Database;

import android.app.Application;


public class Data extends Application {
    private String pathname;
    private int  pathid ;
    private String photoname;
    private int time;
    private  int path_time;

    /**
     * @return
     */
    public String getpathname() {
        return this.pathname;
    }

    /**
     * @param c
     */
    public void setpathname(String c) {
        this.pathname = c;
    }

    /**
     * @return
     */
    public int getpathid() {
        return this.pathid;
    }

    /**
     * @param c
     */
    public void setpathid(int c) {
        this.pathid = c;
    }


    /**
     * @return
     */
    public String  getphotoname() {
        return this.photoname;
    }

    /**
     * @param c
     */
    public void setphotoname(String c) {
        this.photoname = c;
    }

    /**
     * @return
     */
    public int gettime() {
        return this.time;
    }

    /**
     * @param c
     */
    public void settime(int c) {
        this.time = c;
    }

    /**
     * @return
     */
    public int getpath_time() {
        return this.path_time;
    }

    /**
     * @param c
     */
    public void setpath_time(int c) {
        this.path_time = c;
    }


    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }
}