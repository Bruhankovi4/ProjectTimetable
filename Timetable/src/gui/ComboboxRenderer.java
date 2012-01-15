package gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Constants;
import model.Finishable;

public class ComboboxRenderer implements ListCellRenderer {
	ListCellRenderer render;

	public ComboboxRenderer(ListCellRenderer renderer) {
		render = renderer;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component comp = render.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		//System.out.println(value);
		if (value.getClass() != String.class) {
			if (((Finishable) value).isFinished()) {
				if (isSelected)
					comp.setForeground(Constants.SELECTED_DIM);
				else
					comp.setForeground(Constants.DIM);
			}
		}
		return comp;
	}
}
