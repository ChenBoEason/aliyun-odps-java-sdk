package com.aliyun.odps.ml;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Processor")
public class Processor extends AbstractProcessor {
	@XmlElement(name = "Id")
	public String className;
	
	@XmlElement(name = "LibName")
	public String libName;
	
	@XmlElement(name = "RefResource")
	public String refResource;
	
	@XmlElement(name = "Configuration")
	public String configuration;
	
	@XmlElement(name = "Runtime")
	public String runtime;
}
