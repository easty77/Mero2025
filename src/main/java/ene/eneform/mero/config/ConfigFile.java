/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ene.eneform.mero.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Simon
 */
public abstract class ConfigFile {
    // functions moved to FileUtils, but Mero package does not include UtilsENELibrary
protected InputStream loadFile(String strFileName)
{
        System.out.println("loadConfigurationFile: " + strFileName);
        InputStream is = getClass().getResourceAsStream(strFileName);
        if (is == null)
        {
            try
            {
                if (strFileName.indexOf(":") < 0)
                {
                    // relying on current directory
                    File directory = new File (".");
                     try
                     {
                       System.out.println ("Current directory's canonical path: " + directory.getCanonicalPath());
                       System.out.println ("Current directory's absolute  path: " + directory.getAbsolutePath());
                     }
                     catch(Exception e)
                     {
                       System.out.println("Exceptione is ="+e.getMessage());
                     } 
                } 
              is = new FileInputStream(strFileName);
            }
            catch(FileNotFoundException e)
            {
               System.out.println("FileNotFoundException: " + strFileName);
            }
        }
        if (is == null)
        {
            // relying on classpath
            Properties prop = System.getProperties();
            //System.out.println("Classpath=" + prop.getProperty("java.class.path", null));
            System.out.println("File: " + ClassLoader.getSystemClassLoader().getResource(strFileName));
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(strFileName);
        }
        
        return is;
}
protected URL loadURL(String strFileName)
{
        System.out.println("loadConfigurationURL: " + strFileName);
        URL url = getClass().getResource(strFileName);
        if (url == null)
        {
            try
            {
                if (strFileName.indexOf(":") < 0)
                {
                    // relying on current directory
                    File directory = new File (".");
                    try
                     {
                       System.out.println ("Current directory's canonical path: " + directory.getCanonicalPath());
                       System.out.println ("Current directory's absolute  path: " + directory.getAbsolutePath());
                     }
                     catch(Exception e)
                     {
                       System.out.println("Exceptione is ="+e.getMessage());
                     } 
                }
              url = new File(strFileName).toURI().toURL();
            }
            catch(MalformedURLException e)
            {
               System.out.println("MalformedURLException: " + strFileName);
            }
        }
        if (url == null)
        {
            // relying on classpath
            Properties prop = System.getProperties();
            System.out.println("Classpath=" + prop.getProperty("java.class.path", null));

            url = ClassLoader.getSystemClassLoader().getResource(strFileName);
        }
        
        return url;
}
}
