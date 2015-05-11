package be.fedict.eidviewer.lib;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import be.fedict.eidviewer.lib.file.EidFiles;

/* @author Wouter Verhelst */
public class EidTransferHandler extends TransferHandler {
	private static final long serialVersionUID = 5961105281241331113L;
    private static final Logger logger = Logger
    	    .getLogger(EidTransferHandler.class.getName());
	private PCSCEidController eid;
	public int getSourceActions(JComponent comp) {
		logger.fine("enabling drag-and-drop");
		if(eid.equals(null)) {
			return javax.swing.TransferHandler.NONE;
		}
		return javax.swing.TransferHandler.COPY;
	}
	
	public EidTransferHandler(PCSCEidController newEid) {
		eid = newEid;
	}
	protected Transferable createTransferable(JComponent comp) {
		logger.fine("Attempting drag-and-drop");
		switch(eid.getState()) {
		case EID_PRESENT:
		case FILE_LOADED:
			break;
		default:
			logger.fine("No data, aborting drag-and-drop operation");
			return null;
		}
		ByteArrayOutputStream str = new ByteArrayOutputStream();
		try {
			EidFiles.saveToXMLFile(str, eid);
		} catch (Exception e) {
			return null;
		}
		logger.fine("Creating data handler for drag-and-drop operation...");
		return new StringSelection(str.toString());
	}
}
