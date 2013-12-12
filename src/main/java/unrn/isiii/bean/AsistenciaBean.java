package unrn.isiii.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("asistenciaBean")
@Scope("application")
public class AsistenciaBean {

	private static final Logger logger = LoggerFactory
			.getLogger(AsistenciaBean.class);
	
	private int countLoggin;
	
	private int countVotaron;

	private int countSi;
	
	private int countNo;
	

	public int getCountLoggin() {
		return countLoggin;
	}

	public void setCountLoggin(int countLoggin) {
		this.countLoggin = countLoggin;
	}

	public int getCountVotaron() {
		return countVotaron;
	}

	public void setCountVotaron(int countVotaron) {
		this.countVotaron = countVotaron;
	}

	private List<String> presentes = new ArrayList<String>(0);

	public List<String> getPresentes() {
		return presentes;
	}

	public void setPresentes(List<String> presentes) {
		this.presentes = presentes;
	}
	
	

	public int getCountSi() {
		return countSi;
	}

	public void setCountSi(int countSi) {
		this.countSi = countSi;
	}

	public int getCountNo() {
		return countNo;
	}

	public void setCountNo(int countNo) {
		this.countNo = countNo;
	}

	public synchronized void add(String usuario) {
		countLoggin++;
		getPresentes().add(usuario);
	}

	public void iniciovotar() {
		PushContext pushContext = PushContextFactory.getDefault()
				.getPushContext();

		for (String usuario : getPresentes()) {
			pushContext.push("/" + usuario, "");
		}
		
	}
	
	public synchronized Boolean votaronTodos(){
		return (getCountLoggin() == getCountVotaron());
	}
	
	public synchronized void votar(){
		countVotaron++;
	}
	
	public synchronized void votarSi(){
		countSi++;
	}
	
	public synchronized void votarNo(){
		countNo++;
	}

public String realPath(){
  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  return request.getRequestURL().toString().substring(0, request.getRequestURL().toString().length() - 9);

}
}
