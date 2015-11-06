package controller;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import paypal.*;

@ManagedBean(name = "paypal")
@SessionScoped
public class PayPalController {

	private PayPalResult result = new PayPalResult();

	public PayPalResult getResult() {
		return result;
	}

	public void setResult(PayPalResult result) {
		this.result = result;
	}

	public String success() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		PayPalSucess ps = new PayPalSucess();
		this.result = ps.getPayPal(request);
		return "success";
	}

}
