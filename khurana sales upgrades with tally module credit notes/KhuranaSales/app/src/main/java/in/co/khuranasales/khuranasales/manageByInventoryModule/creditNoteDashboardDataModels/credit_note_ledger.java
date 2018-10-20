package in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels;

public class credit_note_ledger {
	public String ledger_name;
	public String ledger_amount;

	public credit_note_ledger()
	{

	}
	public credit_note_ledger(String name, String amount)
	{
		this.ledger_name = name;
		this.ledger_amount = amount;

	}
	public String getLedger_name() {
		return ledger_name;
	}
	public void setLedger_name(String ledger_name) {
		this.ledger_name = ledger_name;
	}
	public String getLedger_amount() {
		return ledger_amount;
	}
	public void setLedger_amount(String ledger_amount) {
		this.ledger_amount = ledger_amount;
	}
	
}
