LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := cpplog
LOCAL_SRC_FILES := cpplog.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
