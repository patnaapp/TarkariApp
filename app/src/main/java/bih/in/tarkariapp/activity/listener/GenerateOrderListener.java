package bih.in.tarkariapp.activity.listener;

public interface GenerateOrderListener
{
    public void onPlaceOrder(int position, boolean isChecked);
    public void onChangeQty(int position, boolean isIncrease);
}
