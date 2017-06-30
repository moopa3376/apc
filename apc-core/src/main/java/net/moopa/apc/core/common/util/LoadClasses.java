/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.moopa.apc.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by LeeAutumn on 30/12/2016.
 * blog: leeautumn.net
 *
 * @author LeeAutumn
 */
public class LoadClasses {
    private static Logger logger = LoggerFactory.getLogger(LoadClasses.class);

    public static List<Class<?>> getClassByPkgName(String pkgName , boolean isRecursive,ClassLoader classLoader){
        logger.info("Load class by package bame: {}",pkgName);

        Enumeration<URL> urls = null;
        List<Class<?>> resultList = new ArrayList<Class<?>>();
        try {
            String packagename = pkgName.replaceAll("\\.",System.getProperty("file.separator"));
            urls = classLoader.getResources(packagename);

            URL temp ;
            while(urls.hasMoreElements()){
                temp = urls.nextElement();

                String protocol = temp.getProtocol();

                if("jar".equals(protocol)){
                    getClassUsingJar(resultList,pkgName,temp,true,classLoader);
                }else if("file".equals(protocol)){
                    getClassUsingFile(resultList,pkgName,temp.getPath(),true,classLoader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private static void getClassUsingJar(List<Class<?>> list,String pkgName,URL pkgPath , boolean isRecursive,ClassLoader classLoader) throws IOException, ClassNotFoundException {
        if(list == null){
            return;
        }
        JarURLConnection jarURLConnection = (JarURLConnection) pkgPath.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();

        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();
            String wholeClassName = jarEntryName.replaceAll("/",".");
            int end = wholeClassName.lastIndexOf(".");

            String jarEntryPkgName = null;
            String className = null;
            if(end > 0){
                className =  wholeClassName.substring(0,end);
                end = className.lastIndexOf(".");
                if(end > 0){
                    jarEntryPkgName = className.substring(0,end);
                }
            }

            if(jarEntryPkgName != null && jarEntryName.endsWith(".class")){
                if(pkgName.equals(jarEntryPkgName)){
                    // we have found the class we want  and   now we are going to load the class
                    list.add(classLoader.loadClass(className));
                }
            }
        }
    }

    private static void getClassUsingFile(List<Class<?>> list,String pkgName,String pkgPath , boolean isRecursive,ClassLoader classLoader) throws ClassNotFoundException {
        if(list == null){
            return;
        }
        File directory = new File(pkgPath);

        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();

            for(File file : files){
                if(file.getName().endsWith(".class")){
                    list.add(classLoader.loadClass(pkgName+"."+file.getName().replaceAll("\\.class","")));
                }else if(file.isDirectory()){
                    if(isRecursive){
                        String subPkgName = pkgName+"."+file.getName();
                        String subPkgPath = pkgPath+System.getProperty("file.separator")+file.getName();
                        getClassUsingFile(list,subPkgName,subPkgPath,isRecursive,classLoader);
                    }
                }
            }
        }
    }
}
