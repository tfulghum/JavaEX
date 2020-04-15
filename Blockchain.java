import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;

import java.awt.Color;

public class Blockchain extends JFrame {

	private JPanel contentPane;
	private JTextField txtAmount;
	private JTextField textTransactionNum;
	private DefaultListModel<String> listModel;
	private JButton btnMerkleRoot;
	private JButton btnMineBlock;

	/**
	 * Launch the application.
	 */
	
	static int difficulty;
	public static void main(String[] args) {
		//DO THIS
		//if we provide arguments, obtain our difficulty as a command line argument
		//if we do not provide arguments, set a default difficulty (between 1 & 5)
		if(args.length > 0)
			difficulty = Integer.parseInt(args[0]);
		else
			difficulty = 3;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Blockchain frame = new Blockchain();
				frame.setVisible(true);
			}
		});

	}

	/**
	 * Create the frame.
	 */
	
	private int transactionNum;
	private int blockNum;
	private String prevHash;
	
	private JTextField blockNumTextField;
	private JTextField prevHashTextField;
	private MerkleTree currentTree;
    private ArrayList<String> transList; //list of transactions
	private Block prevBlock;
	
	public Blockchain() {
		
		//DO THIS!
		//Initialize variables
		transactionNum = 1;
		
		//initialize an array of names
		String[] names = {"Tyler", "Heather", "Billy", "Callie", "Zoe", "McCrea", "Sami", "Tim", "Biscuit", "Howie"};
		
		//initiaze our transaction list
		transList = new ArrayList<String>();
		
		//block number is zero based
		blockNum = 0;
		
		//previous hash can be initiazed to an empty string
		prevHash = "";
		
		
		//GUI 
		// Window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 735);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Label for Transaction #
		JLabel lblTransactionNo = new JLabel("Transaction #");
		lblTransactionNo.setBounds(35, 7, 111, 16);
		contentPane.add(lblTransactionNo);
		
		// Transaction #
		textTransactionNum = new JTextField(Integer.toString(transactionNum));
		textTransactionNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTransactionNum.setBackground(Color.WHITE);
		textTransactionNum.setEditable(false);
		textTransactionNum.setBounds(10, 31, 119, 33);
		contentPane.add(textTransactionNum);
		textTransactionNum.setColumns(10);
		
		// Label for payerCombo
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(180, 7, 111, 16);
		contentPane.add(lblFrom);
		
		// payerCombo
		JComboBox<String> payerCombo = new JComboBox(names);
		payerCombo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		payerCombo.setBounds(139, 32, 119, 33);
		contentPane.add(payerCombo);
		
		// Label for paidCombo
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(315, 7, 111, 16);
		contentPane.add(lblTo);
		
		// paidCombo
		JComboBox<String> paidCombo = new JComboBox(names);
		paidCombo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		paidCombo.setBounds(274, 32, 119, 33);
		contentPane.add(paidCombo);		
		
		// Label for Amount
		JLabel lblAmt = new JLabel("Amount (BTC)");
		lblAmt.setBounds(430, 7, 111, 16);
		contentPane.add(lblAmt);
		
		// Amount
		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAmount.setToolTipText("");
		txtAmount.setBounds(409, 32, 119, 33);
		contentPane.add(txtAmount);
		txtAmount.setColumns(10);
		
		
		// DefaultListModel is our parameter for JList
		listModel = new DefaultListModel<String>();
		
		
		
		
		//DO THIS!
		// Sign Button
		JButton signBtn = new JButton("Sign");
		signBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		signBtn.setBounds(749, 17, 119, 61);
		contentPane.add(signBtn);
		signBtn.addActionListener(new ActionListener()
		{
			// pressed button action
			public void actionPerformed(ActionEvent e)
			{
				//disable other buttons
				btnMerkleRoot.setEnabled(false);
				btnMineBlock.setEnabled(false);
				
				//create a transaction string with the transaction number, the payer, receiver, and the amount
				//you are interested in the payerCombo & paidCombo JComboBox objects (look at the getSelectedItem() method)
				//you are also interested in the txtAmount JTxtAmount object (look at the getText() method)
				String tran = Integer.toString(transactionNum) + " " + (String)payerCombo.getSelectedItem() + " " + (String)paidCombo.getSelectedItem() + " " + txtAmount.getText();
				
				//add the transaction to our list
				tran += " BTC " + DigitalSignature.verifySignature(tran);
				transList.add(tran);
				
				//verify the digital signature of the seller, add the verifcation boolean to our transaction, and then add it to our model
				listModel.addElement(tran);		
				
				//enable Merkle Root button
				btnMerkleRoot.setEnabled(true);

				//increment transaction number
				transactionNum++;
				textTransactionNum.setText(Integer.toString(transactionNum));
			}
		});
		
		// First separator
		JSeparator separator = new JSeparator();
		separator.setBounds(33, 100, 738, 12);
		contentPane.add(separator);
		
		// ScrollPane for JList
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 128, 618, 273);
		contentPane.add(scrollPane);
		
		// JList of Transactions to display
		JList<String> list = new JList<String>(listModel);
		scrollPane.setViewportView(list);
		
		
		
		
		
		
		//DO THIS!
		// "Merkle Root" Button
		btnMerkleRoot = new JButton("Merkle Root");
		
		btnMerkleRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//enable Merkle Root button
				btnMineBlock.setEnabled(true);			
				
				//add a new Merkle Tree
				currentTree = new MerkleTree(transList);
				
				//tell the user that a Merkle Tree has been created
				listModel.addElement("Merkle Tree Created...");
				JList<String> list = new JList<String>(listModel);
				scrollPane.setViewportView(list);
				
				//disable Merkle Root button
				btnMerkleRoot.setEnabled(false);
				
				//reset to a new transaction list
				transList = new ArrayList<String>();
			}
		});
		btnMerkleRoot.setBounds(690, 128, 110, 43);
		btnMerkleRoot.setEnabled(false); 
		contentPane.add(btnMerkleRoot);
		
		// Label "Block Number"
		JLabel lblBlockNumber = new JLabel("Block Number");
		lblBlockNumber.setBounds(690, 179, 111, 16);
		contentPane.add(lblBlockNumber);
		
		// Block # textField
		blockNumTextField = new JTextField("#" + blockNum);
		blockNumTextField.setEditable(false);
		blockNumTextField.setBounds(690, 205, 110, 33);
		blockNumTextField.setBackground(Color.WHITE);
		contentPane.add(blockNumTextField);
		blockNumTextField.setColumns(10);
		
		// Label "Previous Hash"
		JLabel lblPreviousHash = new JLabel("Previous Hash");
		lblPreviousHash.setBounds(689, 248, 111, 16);
		contentPane.add(lblPreviousHash);
		
		// Previous Hash textField
		for(int i = 0; i < 64; i++)
			prevHash += "0";
		prevHashTextField = new JTextField(prevHash);
		prevHashTextField.setEditable(false);
		prevHashTextField.setColumns(10);
		prevHashTextField.setBounds(690, 276, 110, 33);
		prevHashTextField.setBackground(Color.WHITE);
		contentPane.add(prevHashTextField);
		
		DefaultListModel<String> blockChainListModel = new DefaultListModel();
		
		//DO THIS!
		// "Mine Block" Button
		//initialize previous block to null
		prevBlock = null;
		btnMineBlock = new JButton("Mine Block");
		btnMineBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//create a new block and pass in the Block Number, the Merkle Root, the previous Block, and the difficulty				
				Block newBlock = new Block(blockNum, currentTree, prevBlock, difficulty);
				
				//mine the block			
				newBlock.mineBlock();
				
				//update the previous block
				prevBlock = newBlock;
				
				//displaying the block information to the GUI
				ArrayList<String> tempArray = newBlock.toStringArray();
				for(String str : tempArray) {
					blockChainListModel.addElement(str);
				}
				blockChainListModel.addElement("----------------------------------------------------------------------");
				
				//increment block number
				blockNum++;

				//displaying block
				blockNumTextField.setText("#" + blockNum);
				
				//grab the hash from the previous block
				prevHash = prevBlock.getHash();

				
				//add previous hash to be displayed in the GUI
				prevHashTextField.setText(prevHash);
				
				//disable buttons
				btnMerkleRoot.setEnabled(false);
				btnMineBlock.setEnabled(false);
				
				
				//reset the transaction #
				transactionNum = 0;
				
				//display the reset transaction number to the GUI
				textTransactionNum = new JTextField(Integer.toString(transactionNum));
				textTransactionNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
				textTransactionNum.setBackground(Color.WHITE);
				textTransactionNum.setEditable(false);
				textTransactionNum.setBounds(10, 31, 119, 33);
				contentPane.add(textTransactionNum);
				textTransactionNum.setColumns(10);
				
				//clear the list of transactions
				listModel.clear();
			}
		});

		//disable Mine button
		btnMineBlock.setEnabled(false);
		btnMineBlock.setBounds(690, 342, 110, 43);
		contentPane.add(btnMineBlock);
		
		// Second separator
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(33, 419, 715, 12);
		contentPane.add(separator_1);
		
		//create a scrollPane and display the Blockchain 
		JScrollPane scrollPaneBCList = new JScrollPane();
		scrollPaneBCList.setBounds(10, 438, 618, 250);
		contentPane.add(scrollPaneBCList);
		JList<String> blockChainList = new JList<String>(blockChainListModel);
		scrollPaneBCList.setViewportView(blockChainList);

	}
}
