package com.jilaba.control;

import javax.swing.Action;
import javax.swing.JMenu;
/**
 * @author MANOJKUMAR V
 */
public class JilabaMenu extends JMenu{
	
	private static final long serialVersionUID = 1L;
	
	private int moduleId = 0;
	private int menuLevel = 0;
	private String password;
	private String formToLoad = "";
	private String moduleCode = "";
	private String menuCode = "";
	
	public JilabaMenu(){
		super();
	}
	public JilabaMenu(Action arg0){
		super(arg0);
	}
	public JilabaMenu(String arg0){
		super(arg0);
	}
	public JilabaMenu(String arg0,boolean arg1){
		super(arg0,arg1);
	}
	
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFormToLoad() {
		return formToLoad;
	}
	public void setFormToLoad(String formToLoad) {
		this.formToLoad = formToLoad;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public void setMenuDetails(int moduleId,int menuLevel,String menuCode,String moduleCode){
		this.moduleId = moduleId;
		this.menuLevel = menuLevel;
		this.menuCode = menuCode;
		this.moduleCode = moduleCode;
	}
}
