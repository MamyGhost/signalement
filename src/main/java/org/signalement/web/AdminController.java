/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.signalement.entities.Admin;
import org.signalement.entities.Region;
import org.signalement.entities.Signalement;
import org.signalement.repository.AdminRepository;
import org.signalement.repository.RegionRepository;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mamitiana
 */
@Controller
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private SignalementRepository signalementRepository;
    
    @Autowired
    private RegionRepository regionRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @GetMapping("/admin/login")
    public String login(@RequestParam(name="error", defaultValue="0") int error,Model model ){
        if(error == 1){
            model.addAttribute("error", 1);
        }
         return "login";
   
    }
    
    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request){
        
        request.getSession().invalidate();
        return "redirect:/admin/login";
   
    }
    
    
    @GetMapping("/")
    public String index(){
        return "redirect:/admin/login";
    }
    
    
    @PostMapping("/admin/traitementlogin")
    public String traitementlogin(@RequestParam(name="username") String username,@RequestParam(name="password") String password,HttpServletRequest request){
       List<Admin> admin = adminRepository.findAll();
       for(Admin ad:admin){
           if(ad.getUsername().compareTo(username)==0 && ad.getPassword().compareTo(password)==0)
           {
               request.getSession().setAttribute("admin",ad.getId());
               return "redirect:/admin/dashboard";
           }
       }
       
           return "redirect:/admin/login?error=1";
    
    }
    
    @GetMapping("/admin/stat")
    public String stat(){
         return "charts";
   
    }
    
    @GetMapping("/admin/affectation")
    public String affectation(Model model){
         List<Signalement> lista=signalementRepository.findByRegionIsNull();
         List<Region> listar=regionRepository.findAll();
         model.addAttribute("signalement", lista);
          model.addAttribute("region", listar);
         return "affectation";
   
    }
    
     @GetMapping("/admin/updateregion")
    public String updateregion(@RequestParam(name="region") Integer regionid, @RequestParam(name="id") Integer id ){
        Signalement s=signalementRepository.findById(id).get();
        Region r = regionRepository.findById(regionid).get();
        s.setRegion(r);
        signalementRepository.save(s);
        return "redirect:/admin/affectation";
   
    }
    
     @GetMapping("/admin/dashboard")
    public String dashboard(Model model){
        List<Object[]> ru = regionRepository.findRegionup();
        List<Object[]> rl = regionRepository.findRegionlow();
        long totalusers=utilisateurRepository.count();
        long totalsignal=signalementRepository.count();
        
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
 
         return "index";
      
        }
        
  
}
