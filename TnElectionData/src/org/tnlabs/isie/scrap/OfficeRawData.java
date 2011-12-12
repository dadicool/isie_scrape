package org.tnlabs.isie.scrap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Sami
 *
 */
public class OfficeRawData {
	private int nbVoters = 0;
	private int nbRegistred = 0;
	private int nbDelivredPaper = 0;
	private int nbUsedPaper = 0;
	private int nbUnusedPaper = 0;
	private int nbLostPaper = 0;
	private int nbWhiteVotes = 0;
	private int nbCanceledVotes = 0;
	private int nbCountedVotes = 0;
	List<CandidateList> candidateList=null;
	
	public OfficeRawData(){
		
	}

	public int getNbVoters() {
		return nbVoters;
	}

	public int getNbRegistred() {
		return nbRegistred;
	}

	public int getNbDelivredPaper() {
		return nbDelivredPaper;
	}

	public int getNbUsedPaper() {
		return nbUsedPaper;
	}

	public int getNbUnusedPaper() {
		return nbUnusedPaper;
	}

	public int getNbLostPaper() {
		return nbLostPaper;
	}

	public int getNbWhiteVotes() {
		return nbWhiteVotes;
	}

	public int getNbCanceledVotes() {
		return nbCanceledVotes;
	}

	public int getNbCountedVotes() {
		return nbCountedVotes;
	}

	public List<CandidateList> getCandidateList() {
		return candidateList;
	}
	
	
	public void setNbVoters(int nbVoters) {
		this.nbVoters = nbVoters;
	}

	public void setNbRegistred(int nbRegistred) {
		this.nbRegistred = nbRegistred;
	}

	public void setNbDelivredPaper(int nbDelivredPaper) {
		this.nbDelivredPaper = nbDelivredPaper;
	}

	public void setNbUsedPaper(int nbUsedPaper) {
		this.nbUsedPaper = nbUsedPaper;
	}

	public void setNbUnusedPaper(int nbUnusedPaper) {
		this.nbUnusedPaper = nbUnusedPaper;
	}

	public void setNbLostPaper(int nbLostPaper) {
		this.nbLostPaper = nbLostPaper;
	}

	public void setNbWhiteVotes(int nbWhiteVotes) {
		this.nbWhiteVotes = nbWhiteVotes;
	}

	public void setNbCanceledVotes(int nbCanceledVotes) {
		this.nbCanceledVotes = nbCanceledVotes;
	}

	public void setNbCountedVotes(int nbCountedVotes) {
		this.nbCountedVotes = nbCountedVotes;
	}

	public void setCandidateList(List<CandidateList> candidateList) {
		this.candidateList = candidateList;
	}
	public void addList(CandidateList list) {
		if (candidateList==null)
			candidateList=new ArrayList<CandidateList>();
		candidateList.add(list);
	}
	
	public String toJson(String circonscription,String delegation, String center, String bureau,LabelProvider lp) throws JSONException{
		String cLabel=lp.getCircLabel(circonscription);
		String dLabel=lp.getDelLabel(circonscription,delegation);
		String eLabel=lp.getCenterLabel(circonscription,delegation,center);
		String bLabel=lp.getOfficeLabel(circonscription,delegation,center,bureau);

		JSONObject root=new JSONObject();
		
		JSONObject circonscriptionObj=new JSONObject();
		circonscriptionObj.put("code",circonscription);
		circonscriptionObj.put("name",cLabel);
		root.put("circonscription",circonscriptionObj);
		
		JSONObject delegationObj=new JSONObject();
		delegationObj.put("code",delegation);
		delegationObj.put("name",dLabel);
		root.put("delegation",delegationObj);
		
		JSONObject centerObj=new JSONObject();
		centerObj.put("code",center);
		centerObj.put("name",eLabel);
		root.put("centre_vote",centerObj);
		
		JSONObject bureauObj=new JSONObject();
		bureauObj.put("code",bureau);
		bureauObj.put("name",bLabel);
		root.put("bureau_vote",bureauObj);
		
		JSONObject electeurs=new JSONObject();
		electeurs.put("enregistre",nbRegistred);
		electeurs.put("votant",nbVoters);
		JSONObject bulletins=new JSONObject();
		bulletins.put("delivre", nbDelivredPaper);
		bulletins.put("non_utilise", nbUnusedPaper);
		bulletins.put("endommage", nbLostPaper);
		bulletins.put("blancs", nbWhiteVotes);
		bulletins.put("annule", nbCanceledVotes);
		bulletins.put("dans_urne", nbVoters);
		bulletins.put("correct", nbCountedVotes);
		JSONArray list=new JSONArray();
		Iterator<CandidateList> iter=candidateList.iterator();
		while (iter.hasNext()){
			CandidateList cl=iter.next();
			JSONObject l=new JSONObject();
			l.put("num", cl.getId());
			l.put("name", cl.getName());
			l.put("vote", cl.getNVotes());
			l.put("pourcentage", cl.getPercent());
			list.put(l);
		}
		JSONObject resultat=new JSONObject();
		resultat.put("electeurs", electeurs);
		resultat.put("bulletins", bulletins);
		resultat.put("listes", list);
		root.put("resultat", resultat);
		return root.toString();
	}
	public static class CandidateList {
		private int id;
		private String name;
		private int nVotes;
		private float percent;
		
		public CandidateList(int id,String name,int nVotes,float percent){
			this.id=id;
			this.name=name;
			this.nVotes=nVotes;
			this.percent=percent;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getNVotes() {
			return nVotes;
		}

		public float getPercent() {
			return percent;
		}
		
		
	}

}
