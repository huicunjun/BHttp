package com.ldw.bhttp.cahce;

import java.io.File;

import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;

/**
 * @date 2020/5/22 17:22
 */
public class Cahce {
    DiskLruCache diskLruCache;

    public Cahce() {
        this.diskLruCache =  DiskLruCacheFactory.newDiskLruCache(FileSystem.SYSTEM,new File(""),1,1,1);
    }
}
