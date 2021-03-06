import java.awt.*;
import java.awt.event.*;
import ij.plugin.frame.*;
import ij.*;
import ij.gui.*;

/**
*	Adapted from Image Processing Demo.
*/
public class PoissonNTFprogress extends PlugInFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3728787123075205236L;
	Panel panel;
	int previousID;
	static Frame instance;
	PoissonNTF_ pntfinstance;
	
	public PoissonNTFprogress(PoissonNTF_ pntf) {
		super("PoissonNTF is running...");
		pntfinstance=pntf;
		if (IJ.versionLessThan("1.39t"))
			return;
		if (instance!=null) {
			instance.toFront();
			return;
		}
		instance = this;
		addKeyListener(IJ.getInstance());

		setLayout(new FlowLayout());
		panel = new Panel();
		panel.setLayout(new GridLayout(1, 1, 2, 2));
		addButton("Cancel");
		addButton("Least Squares Solution");
		add(panel);

		pack();
		GUI.center(this);
		setVisible(true);
	}
	
	void addButton(String label) {
		Button b = new Button(label);
		b.addActionListener(this);
		b.addKeyListener(IJ.getInstance());
		panel.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if (label==null)
			return;
		if (label=="Cancel")
		{
			pntfinstance.cancelled=true;
			dispose();
			instance = null;			
		}
		else if  (label=="Least Squares Solution")
		{
			pntfinstance.interrupted=true;
			dispose();
			instance = null;			
		}
	}

	public void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID()==WindowEvent.WINDOW_CLOSING) {
			pntfinstance.cancelled=true;
			dispose();
			instance = null;			
		}
	}	
	public void closeWindow()
	{
		dispose();
		instance = null;					
	}
	
	
}
