package com.jilaba.control;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.jilaba.control.JMenuEnum.FormType;

/**
 * @author MANOJKUMAR V
 * Jilaba Menu Item has password , which form to load to this menu click
 */
public class JilabaMenuItem extends JMenuItem{

	
	private static final long serialVersionUID = 1L;
	
	private int moduleId = 0;
	private int menuLevel = 0;
	private String password;
	private String formToLoad = "";
	private String moduleCode = "";
	private String menuCode = "";
	
	private FormType formType = FormType.SAVE_VIEW_EDIT;
	
	public JilabaMenuItem(){
		super();
	}
	public JilabaMenuItem(Action arg0){
		super(arg0);
	}
	public JilabaMenuItem(Icon arg0){
		super(arg0);
	}
	public JilabaMenuItem(String arg0){
		super(arg0);
	}
	public JilabaMenuItem(String arg0,Icon arg1){
		super(arg0,arg1);
	}
	public JilabaMenuItem(String arg0,int arg1){
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
	
	public FormType getFormType() {
		return formType;
	}
	public void setFormType(FormType formType) {
		this.formType = formType;
	}
	public void setMenuDetails(int moduleId,int menuLevel,String menuCode,String moduleCode){
		this.moduleId = moduleId;
		this.menuLevel = menuLevel;
		this.menuCode = menuCode;
		this.moduleCode = moduleCode;
	}
	
}
