package com.openxu.core.utils;

/**
 * @Author: lbing
 * @CreateDate: 2020/4/20 10:17
 * @Description: 方便插入日志
 * @UpdateRemark:
 */
public class ProcessLogUtils {
    public static String JumpType = "Jump";
    public static String NetType = "Net";
    public static String ClickType = "Click";
    public static String ScanType = "Scan";
    public static String CrashType = "Crash";
    public static String DBType = "Sql";
    public static String JudgeType = "Judge";
    public static String UpdateType = "Update";
    public static String OtherType = "Other";

    public static void insertOne(String type, final String content) {
       /* ProcessLogEntityDao dao = (ProcessLogEntityDao) GreenDaoManager.getInstance().getDao(ProcessLogEntityDao.class);
        SharedData sharedData = SharedData.getInstance();
        if (type.equals(NetType)) {
            dao.insert(new ProcessLogEntity("【" + type + "】" + content, type, sharedData.getUser().getUserID()));
            NetListenerUtils.getInstance().setOnNetListener(new NetListenerUtils.NetListener() {
                @Override
                public void onNetListener(String netStatusResult) {
                    String netcontent = content + "  补充网络状态：" + netStatusResult;
                    if (sharedData != null && sharedData.getUser() != null) {
                        dao.insert(new ProcessLogEntity("【" + type + "】" + netcontent, type, sharedData.getUser().getUserID()));
                    }
                }
            });
        } else {
            if (sharedData != null && sharedData.getUser() != null) {
                dao.insert(new ProcessLogEntity("【" + type + "】" + content, type, sharedData.getUser().getUserID()));
            }
        }*/
    }

    public static void queryList(CreateLog createLog) {
/*

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //        先清除原先缓存的文件，然后生成新的文件
                FpcFiles.deleteFiles(new File(FpcFiles.getAppPath() + "/" + "processlog"));//删除缓存文件
                FLog.e("清除旧文件,开始生成新文件");
//                FToast.info("清除旧文件,开始生成新文件");
            }

            @Override
            protected Integer doInBackground(Void... voids) {
                ProcessLogEntityDao dao = (ProcessLogEntityDao) GreenDaoManager.getInstance().getDao(ProcessLogEntityDao.class);
                List<ProcessLogEntity> list = dao.queryBuilder().build().list();
//               将数据库中数据写入到文件
                FileUtils.writeListLogFile(list, "processlog", "log");
                Log.e("TAG", "日志文件生成成功");
                File[] sqlLogsFiles = FileUtils.readFiles("processlog");
                for (int i = 0; i < sqlLogsFiles.length; i++) {
                    ZipUtils.zipFile(sqlLogsFiles[i], "processlogzip", "日志压缩");
                }
                Log.e("TAG", "日志文件压缩成功");
                return list.size();
            }

            @Override
            protected void onPostExecute(Integer size) {
                super.onPostExecute(size);
                FLog.e("日志文件生成完毕,共" + size + "条");
//                FToast.info("日志文件生成完毕,共" + size + "条");
                ProcessLogEntityDao dao = (ProcessLogEntityDao) GreenDaoManager.getInstance().getDao(ProcessLogEntityDao.class);
                dao.deleteAll();
                if (createLog != null) {
                    createLog.onCreateLogComplete();
                }
            }
        }.execute();
*/

    }

    public static interface CreateLog {
        void onCreateLogComplete();
    }
}
