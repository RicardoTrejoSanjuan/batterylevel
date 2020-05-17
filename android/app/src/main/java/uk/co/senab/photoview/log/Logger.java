package uk.co.senab.photoview.log;

public interface Logger {
  int d(String paramString1, String paramString2);
  
  int d(String paramString1, String paramString2, Throwable paramThrowable);
  
  int e(String paramString1, String paramString2);
  
  int e(String paramString1, String paramString2, Throwable paramThrowable);
  
  int i(String paramString1, String paramString2);
  
  int i(String paramString1, String paramString2, Throwable paramThrowable);
  
  int v(String paramString1, String paramString2);
  
  int v(String paramString1, String paramString2, Throwable paramThrowable);
  
  int w(String paramString1, String paramString2);
  
  int w(String paramString1, String paramString2, Throwable paramThrowable);
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\log\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */