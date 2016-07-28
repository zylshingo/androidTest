#include <jni.h>
#include "com_jikexueyuan_cpplog_LogMsg.h"
#include <android/log.h>

#define LOG_TAG "OUTPUT"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)


/*
 * Class:     com_jikexueyuan_cpplog_LogMsg
 * Method:    logMsg
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_jikexueyuan_cpplog_LogMsg_logMsg
  (JNIEnv *, jclass){
	LOGD("This log is from CPP");
}
