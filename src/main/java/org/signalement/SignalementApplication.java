package org.signalement;

import java.util.Date;
import org.signalement.entities.Utilisateur;
import org.signalement.entitiesMDB.SignalementmDB;
import org.signalement.repositorymDB.SignalementRepositorymDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SignalementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignalementApplication.class, args);
	}
        
        @Bean
        CommandLineRunner Start(SignalementRepositorymDB signalementRepository){
            return args->{
////                signalementRepository.deleteAll();
////                SignalementmDB signal=new SignalementmDB();
////                signal.setId(1);
////                signal.setDaty(new Date());
////                signal.setDescription("gg");
////                signal.setLatitude(19.66464);
////                signal.setLongitude(44.546);
////                Utilisateur test=new Utilisateur();
////                test.setDateinsc(new Date());
////                test.setEmail("testa@gmail.com");
////                test.setId(2);
////                test.setPassword("12345678");
////                signal.setUtilisateur(test);
////         
////                signalementRepository.save(signal);
//                  Utilisateur test=new Utilisateur();
//                  test.setId(2);
               //   System.out.println("Testa signalment:"+signalementRepository.findByEmail("testa5@gmail.com").get(0).getLatitude());

            };
                    
        }

}
