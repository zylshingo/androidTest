// IMyAidlInterface.aidl
package com.jikexueyuan.app01;

// Declare any non-default types here with import statements
import com.jikexueyuan.app01.ICallback;

interface IMyAidlInterface {

    void registCallback(ICallback callback);
    void unregistCallback(ICallback callback);

}
