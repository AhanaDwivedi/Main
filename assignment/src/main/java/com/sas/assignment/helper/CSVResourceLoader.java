package com.sas.assignment.helper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.sas.assignment.bean.MenuItem;

/**
 * 
 */

/**
 * @author 149039
 *
 */
public class CSVResourceLoader implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;
	private Resource resource;
	 
    public Resource getResource() {
        return resource;
    }
 
    public void setResource(Resource resource) {
        this.resource = resource;
    }
	public InputStream readResource() {	
		BufferedReader br = null;		
		InputStream in = null;
		try {
			in = this.resource.getInputStream();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return in;
	}

	public void setResourceLoader(ResourceLoader arg0) {
		this.resourceLoader = resourceLoader;
		
	}

	
}
