package com.jilaba.calls.start;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.jilaba.calls.common.CustomFonts;
import com.jilaba.calls.config.ApplicationConfig;
import com.jilaba.calls.forms.FrmCallsEntry;
import com.jilaba.calls.forms.FrmDailyActivity;
import com.jilaba.calls.forms.FrmDataValidation;
import com.jilaba.calls.forms.FrmDevCallAssign;
import com.jilaba.calls.forms.FrmDevCalls;
import com.jilaba.calls.forms.FrmLogin;
import com.jilaba.calls.forms.FrmReadyCalls;
import com.jilaba.security.Validation;

public class ApplicationStart {

	public static void main(String[] args) {

		try {

			// to merge button focus and cursor foucs
			UIManager.put("Button.defaultButtonFollowsFocus", true);

			// to set custom message font
			UIManager.put("OptionPane.messageFont", CustomFonts.BOLDFONT14);

			AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(
					ApplicationConfig.class);
			Applicationmain.setAbstractApplicationContext(applicationContext);

			FrmDailyActivity frmDailyActivity = Applicationmain.getAbstractApplicationContext()
					.getBean(FrmDailyActivity.class);
			frmDailyActivity.setVisible(true);

//			
//			FrmLogin frmLogin = Applicationmain.getAbstractApplicationContext().getBean(FrmLogin.class);
//			frmLogin.setVisible(true);

		} catch (Exception e) {
			
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();

		}

	}

}
