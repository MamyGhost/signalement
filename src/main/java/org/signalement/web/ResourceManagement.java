/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.web;

import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.signalement.entities.Photo;
import org.signalement.entities.Utilisateur;
import org.signalement.entities.Region;
import org.signalement.entities.Signalement;
import org.signalement.entities.Userfront;
import org.signalement.repository.RegionRepository;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.UserfrontRepository;
import org.signalement.repository.PhotoRepository;
import org.signalement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mamitiana
 */
@Controller
public class ResourceManagement {
    
    @Autowired
    private SignalementRepository signalementRepository;
    
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private UserfrontRepository userFrontRepository;

    @Autowired
    private UtilisateurRepository userMobileRepository;


    @Autowired
    private PhotoRepository photoRep;
    
    
    
        
    
    @GetMapping("/manageResource/userFrontRsrc")
    public String manageUserFront(Model model){
        List<Userfront> list=userFrontRepository.findAll();
        model.addAttribute("fontUserList", list);
        return "userFrontList";
    }

    // @GetMapping("/manageResource/signalementRsrc/{pagination}")
    // public String manageSignalement(@PathVariable(name="pagination") Integer page,Model model){
    //     List<Signalement> lista=signalementRepository.findAll();
    //     model.addAttribute("signalement", lista);
    //     model.addAttribute("auteur","vide");
    //     return "signalementList";
    // }

    @GetMapping("/manageResource/signalementRsrc/{pagination}")
    public String manageSignalement(@PathVariable(name="pagination") Integer page,Model model){
        int nbrElement=5;
        double isa=signalementRepository.count();
        int pageNumber=(int)(Math.ceil(isa/nbrElement));
        int first=page*nbrElement;
        List<Signalement> lista=signalementRepository.findWithPagination(first,nbrElement);
        model.addAttribute("signalement", lista);
        model.addAttribute("auteur","vide");
        model.addAttribute("nbrPage",pageNumber);
        model.addAttribute("page",page);
        return "signalementList";
    }


    @GetMapping("/manageResource/signalementUserRsr/{id}/{page}")
    public String manageSignalementUser(@PathVariable(name="id") Integer id,@PathVariable(name="page") Integer page,Model model){
        int nbrElement=5;
        Utilisateur user=userMobileRepository.findById(id).get();
        double isa=user.getSignalementList().size();
        int pageNumber=(int)(Math.ceil(isa/nbrElement));
        int first=page*nbrElement;
        List<Signalement> lista=signalementRepository.findByUserWithPagination(id,first,nbrElement);
        model.addAttribute("signalement", lista);

        model.addAttribute("auteur",user.getEmail());

        model.addAttribute("nbrPage",pageNumber);
        model.addAttribute("page",page);
        model.addAttribute("user",id);

        return "signalementList";
    }

    @GetMapping("/manageResource/modifUF/{id}")
    public String manageSignalement(Model model,@PathVariable(name="id") Integer id){
        Userfront s=userFrontRepository.findById(id).get();
        List<Region> listRegion=regionRepository.findAll();
        model.addAttribute("user",s);
        model.addAttribute("region",listRegion);
        return "modifUserFront";
    }

    @GetMapping("/manageResource/updateUF")
    public String updateregion(@RequestParam(name="region") Integer regionid,@RequestParam(name="id") Integer id,@RequestParam(name="username")  String usrName,@RequestParam(name="mdp") String mdp){
        Userfront usr= userFrontRepository.findById(id).get();
        Region r = regionRepository.findById(regionid).get();
        usr.setRegion(r);
        usr.setUsername(usrName);
        String sha = "";
            
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(mdp.getBytes("utf8"));
            sha = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        } 
        usr.setPassword(sha);
        userFrontRepository.save(usr);
        return "redirect:/manageResource/userFrontRsrc";
   
    } 

    @GetMapping("/manageResource/formulaireUF")
    public String create(Model model){
        List<Region> listRegion = regionRepository.findAll();
        Userfront empty=new Userfront();
        model.addAttribute("region",listRegion);
        model.addAttribute("userfront",empty);
        return "formulaireUF";
   
    }

    @GetMapping("/manageResource/formulaireMB")
    public String createMB(Model model){
        Utilisateur user=new Utilisateur();
        model.addAttribute("utilisateur",user);
        return "formulaireMB";
   
    }

    @PostMapping("/manageResource/saveMB")
    public String insertMB(@ModelAttribute("utilisateur") Utilisateur user,BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){

        }
        String sha = "";
            
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(user.getPassword().getBytes("utf8"));
            sha = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        } 
        user.setPassword(sha);
        userMobileRepository.save(user);
        return "redirect:/manageResource/userMobileRsrc/0";
   
    } 

    @PostMapping("/manageResource/saveUF")
    public String insertUF(@ModelAttribute("userfront") Userfront user,BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }
        String sha = "";
            
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(user.getPassword().getBytes("utf8"));
            sha = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        } 
        user.setPassword(sha);
        userFrontRepository.save(user);
        return "redirect:/manageResource/userFrontRsrc";
   
    }  
    @GetMapping("/manageResource/deleteUF/{id}")
    public String deleteUF(@PathVariable(name="id") Integer id){
        Userfront user=userFrontRepository.findById(id).get();
        userFrontRepository.delete(user);
        return "redirect:/manageResource/userFrontRsrc";
   
    } 
    @GetMapping("/manageResource/deleteSIGN/{id}")
    public String deleteSIGN(@PathVariable(name="id") Integer id){
        Signalement sign=signalementRepository.findById(id).get();
        signalementRepository.delete(sign);
        return "redirect:/manageResource/signalementRsrc/0";
   
    } 

    @GetMapping("/manageResource/viewSIGN/{id}/{page}")
    public String detailSIGN(@PathVariable(name="id") Integer id,@PathVariable(name="page") Integer page,Model model){
        Signalement sign=signalementRepository.findById(id).get();
        List<Photo> photos=photoRep.findBySignalement(sign);
         List<Region> listRegion = regionRepository.findAll();
        model.addAttribute("region",listRegion);
        model.addAttribute("sign",sign);
        model.addAttribute("photos",photos);
        model.addAttribute("user",0);
        model.addAttribute("page",page);
        return "detailSignalement";
   
    } 

    @GetMapping("/manageResource/viewSignUser/{id}/{user}/{page}")
    public String detailSignUser(@PathVariable(name="id") Integer id,@PathVariable(name="user") Integer user,@PathVariable(name="page") Integer page,Model model){
        Signalement sign=signalementRepository.findById(id).get();
        List<Photo> photos=photoRep.findBySignalement(sign);
        List<Region> listRegion = regionRepository.findAll();
        model.addAttribute("region",listRegion);
        model.addAttribute("sign",sign);
        model.addAttribute("photos",photos);
        model.addAttribute("user",user);
        model.addAttribute("page",page);
        return "detailSignalement";
   
    } 


    @GetMapping("/manageResource/regionSignUser/{id}/{user}/{page}")
    public String regionSignUser(@PathVariable(name="id") Integer id,@PathVariable(name="user") Integer user,@PathVariable(name="page") Integer page,@RequestParam(name="region") Integer regionid,Model model){
        Signalement sign=signalementRepository.findById(id).get();
        Region r = regionRepository.findById(regionid).get();
        sign.setRegion(r);
        signalementRepository.save(sign);
        String retour="redirect:/manageResource/viewSignUser/"+id+"/"+user+"/"+page;
        return retour;
   
    }

    @GetMapping("/manageResource/regionSign/{id}/{page}")
    public String regionSign(@PathVariable(name="id") Integer id,@PathVariable(name="page") Integer page,@RequestParam(name="region") Integer regionid,Model model){
        Signalement sign=signalementRepository.findById(id).get();
        Region r = regionRepository.findById(regionid).get();
        sign.setRegion(r);
        signalementRepository.save(sign);
        String retour="redirect:/manageResource/viewSIGN/"+id+"/"+page;
        return retour;
   
    }


    @GetMapping("/manageResource/userMobileRsrc/{page}")
    public String manageUserMobile(@PathVariable(name="page") Integer page,Model model){
        int nbrElement=5;
        double isa=userMobileRepository.count();
        int pageNumber=(int)(Math.ceil(isa/nbrElement));
        int first=page*nbrElement;
        List<Utilisateur> list=userMobileRepository.findWithPagination(first,nbrElement);
        model.addAttribute("mobileList", list);
        model.addAttribute("nbrPage",pageNumber);
        model.addAttribute("page",page);
        return "userMobileList";
    }

    @GetMapping("/manageResource/regionStat")
    public String stat(Model model){
        List<Object[]> ru = regionRepository.findRegionup();
        List<Object[]> rl = regionRepository.findRegionlow();
        long totalusers=userMobileRepository.count();
        long totalsignal=signalementRepository.count();
        List<Region> listRegion = regionRepository.findAll();
        model.addAttribute("region",listRegion);
        
        String regionup="Aucun";
        String regionlow="Aucun";
        Long up=0L;
        Long down=0L;
        
        for(Object[] ruu:ru){
            if(up<(Long)ruu[1]){
                up=(Long)ruu[1];
                regionup=(String)ruu[0];
            }
        }
        
        for(Object[] rll:rl){
            if(down<(Long)rll[1]){
                down=(Long)rll[1];
                regionlow=(String)rll[0];
            }
        }
            System.out.println("");
         model.addAttribute("regionup",regionup);
         model.addAttribute("regionlow",regionlow);
         model.addAttribute("up",up);
         model.addAttribute("down",down);
         model.addAttribute("totalusers", totalusers);
         model.addAttribute("totalsignal", totalsignal);
 
         return "regionDashboard";
      
    }


}
