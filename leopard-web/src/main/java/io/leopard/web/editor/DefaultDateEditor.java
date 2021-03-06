package io.leopard.web.editor;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import io.leopard.burrow.util.DateTime;
import io.leopard.burrow.util.DateUtil;

@Deprecated
public class DefaultDateEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// System.out.println("DefaultDateEditor setAsText:" + text);
		if (text == null || text.length() == 0 || "undefined".equals(text)) {
			super.setValue(null);
		}
		else if (DateTime.isDateTime(text)) {
			if ("1970-01-01 08:00:00".equals(text)) {
				super.setValue(new Date(1));
			}
			else {
				super.setValue(DateUtil.toDate(text));
			}
		}
		else if (DateTime.isDate(text)) {
			super.setValue(DateUtil.toDate(text + " 00:00:00"));
		}
		else {
			long time = Long.parseLong(text);
			if (time > 0) {
				super.setValue(new Date(time));
			}
			else if (time == 0) {
				super.setValue(new Date(1));
			}
			else {
				throw new IllegalArgumentException("未知时间格式[" + text + "].");
			}
		}
	}

}
