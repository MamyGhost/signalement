create database signalement;
use signalement;

create table province(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Nom varchar(250)
)ENGINE=InnoDB;

create table region(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Nom varchar(250),
	Province INT,
	FOREIGN KEY(Province) REFERENCES province(Id)
)ENGINE=InnoDB;

create table utilisateur(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Username varchar(125),
	Password varchar(250)
)ENGINE=InnoDB;

create table admin(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Username varchar(125),
	Password varchar(250)

)ENGINE=InnoDB;

create table userfront(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Username varchar(125),
	Password varchar(250),
	Region int ,
	FOREIGN KEY(Region) REFERENCES region(Id)
)ENGINE=InnoDB;


create table tokenfront(
	 Id INT NOT NULL AUTO_INCREMENT primary key,
	 Userfront int,
	 Token varchar(700),
	 Dateexp date,
	 FOREIGN KEY(Userfront) REFERENCES userfront(Id)
)ENGINE=InnoDB;

create table tokenmobile(
	 Id INT NOT NULL AUTO_INCREMENT primary key,
	 Utilisateur int,
	 Token varchar(700),
	 Dateexp date,
	 FOREIGN KEY(Utilisateur) REFERENCES utilisateur(Id)
)ENGINE=InnoDB;


create table type(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Nom varchar(250)
)ENGINE=InnoDB;


create table signalNew(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Titre varchar(250)
)ENGINE=InnoDB;

create table statut(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Etat varchar(75)
)ENGINE=InnoDB;

create table signalement(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Signalnew int,
	Utilisateur int ,
	Description varchar(1000),
	Statut int ,
	Region int ,
	Type int ,
	daty date,
	Latitude decimal(10,8),
	Longitude decimal(10,8),
	FOREIGN KEY(Utilisateur) REFERENCES utilisateur(Id),
	FOREIGN KEY(Type) REFERENCES type(Id),
	FOREIGN KEY(Region) REFERENCES region(Id),
	FOREIGN KEY(Statut) REFERENCES statut(Id),
	FOREIGN KEY(Signalnew) REFERENCES signalNew(Id)
)ENGINE=InnoDB;


create table photo(
	Id INT NOT NULL AUTO_INCREMENT primary key,
	Signalement int ,
	Photo varchar(500),
	FOREIGN KEY(Signalement) REFERENCES signalement(Id)
)ENGINE=InnoDB;



insert into admin values
	(null,"admin","0000");

insert into utilisateur values
	(null,"user","0000");

insert into userfront values(null,"mamy.@gmail.com","mamy12345",1);
insert into userfront values(null,"Rakoto.@gmail.com","Rakoto12345",2);
insert into userfront values(null,"Jean.@gmail.com","Jean12345",3);
insert into userfront values(null,"Marcel.@gmail.com","Marcel12345",4);
insert into userfront values(null,"Olivier.@gmail.com","Olivier12345",5);
insert into userfront values(null,"Gerard.@gmail.com","Gerard12345",6);
insert into userfront values(null,"Hasina.@gmail.com","Hasina12345",7);
insert into userfront values(null,"jose.@gmail.com","jose12345",8);
insert into userfront values(null,"Valy.@gmail.com","Valy12345",9);
insert into userfront values(null,"Medard.@gmail.com","Medard12345",10);
insert into userfront values(null,"Kohen.@gmail.com","Kohen12345",11);
insert into userfront values(null,"mahery.@gmail.com","mahery12345",12);
insert into userfront values(null,"Bernard.@gmail.com","Bernard12345",13);
insert into userfront values(null,"zaka.@gmail.com","zaka12345",14);
insert into userfront values(null,"ratrema.@gmail.com","ratrema12345",15);
insert into userfront values(null,"Mpandry.@gmail.com","Mpandry12345",16);
insert into userfront values(null,"Moise.@gmail.com","Moise12345",17);
insert into userfront values(null,"fandry.@gmail.com","fandry12345",18);
insert into userfront values(null,"maurice.@gmail.com","maurice12345",19);
insert into userfront values(null,"Vatsy.@gmail.com","Vatsy12345",20);
insert into userfront values(null,"tsangy.@gmail.com","tsangy12345",21);
insert into userfront values(null,"Mitony.@gmail.com","Mitony12345",22);

insert into type values
	(null,"vol"),
	(null,"accident"),
	(null,"autres");

insert into province values
	(null,"Antsiranana"),
	(null,"Antananarivo"),
	(null,"Mahajanga"),
	(null,"Toamasina"),
	(null,"Fianarantsoa"),
	(null,"Toliara");

insert into region values
	(null,"Diana",1),
	(null,"Sava",1),
	(null,"Itasy",2),
	(null,"Analamanga",2),
	(null,"Vakinakaratra",2),
	(null,"Bongolava",2),
	(null,"Sofia",3),
	(null,"Boeny",3),
	(null,"Betsiboka",3),
	(null,"Melaky",3),
	(null,"Alaotra Mangoro",4),
	(null,"Antsinana",4),
	(null,"Analanjorofo",4),
	(null,"Amoron'i Mania",5),
	(null,"Haute Matsiatra",5),
	(null,"Vatovavy Fitovinany",5),
	(null,"Atsimo Atsinana",5),
	(null,"Ihorombe",5),
	(null,"Menabe",6),
	(null,"Atsime Andrefana",6),
	(null,"Androy",6),
	(null,"Anosy",6);

insert into signalNew values(null,"Accident de voitures su la route de RN2");
insert into signalNew values(null,"Accident de voitures su la route de RN1");
insert into signalNew values(null,"vol faites par des Gang");
insert into signalNew values(null,"Enlèvement");


insert into statut values
	(null,"Nouveau"),
	(null,"En cours"),
	(null,"Terminé");

update signalement set signalnew=3 where id=3;

insert into signalement values(null,1,1,"Aucun description",1,3,3,"2022-01-15",-19.715,46.75781);
insert into signalement values(null,2,1,"grave accident",1,1,2,"2022-10-26",-19.012,46.82315);
insert into signalement values(null,3,1,"vol d'un coq pure sang Bruce Lee",2,1,1,"2022-10-30",-19.412,46.72315);
insert into signalement values(null,3,1,"vol d'un voiture",2,22,1,"2022-10-29",-19.325,46.42153);
insert into signalement values(null,2,1,"Grave accident",3,2,2,"2022-09-10",-21.325,54.42153);
insert into signalement values(null,2,1,"Grave accident",3,4,2,"2022-09-13",-18.325,44.42153);
insert into signalement values(null,2,1,"Grave accident",1,null,2,"2022-06-10",-21.687,54.96358);
insert into signalement values(null,4,1,"Kidnapping d'un enfant",1,null,3,"2022-06-26",-16.857,46.42153);
insert into signalement values(null,4,1,"Kidnapping d'un enfant",2,4,3,"2022-10-27",-19.857,46.49123);

insert into photo values
	(null,1,"test.png"),
	(null,1,"test2.png");

drop database signalement;


select * from signalement s join userfront u on s.region=u.region join tokenfront t on u.id=t.userfront where t.token='a0540b15d3810565f733dbf4065ba32907f2284c';

<script type="javascript">
            // On initialise la latitude et la longitude de Paris (centre de la carte)
            var lat = 48.852969;
            var lon = 2.349903;
            var macarte = null;
         //   var signalement = [[${signalement}]];
            
            // Fonction d'initialisation de la carte
            function initMap() {
                // Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
                macarte = L.map('map').setView([lat, lon], 11);
                // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
                L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                    // Il est toujours bien de laisser le lien vers la source des données
                    attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
                    minZoom: 1,
                    maxZoom: 20
                }).addTo(macarte);
//                for (s in signalement) {
//		var marker = L.marker([signalement[s].latitude, [signalement[s].longitude]).addTo(macarte);
	}    
            }
            window.onload = function(){
		// Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
		initMap();
                //clicktr();
            };
            
//            function clicktr(){
//                let tr=$(".clicktr");
//                tr.click(function(){
//                // Holds the product ID of the clicked element
//                let latnew=tr.find(".lat").text();
//                let lonnew=tr.find(".long").text();
//                macarte.setView([latnew, lonnew], 11);
//              });
//            }
            </script>




{
    "id": 3,
    "description": "Aucun description",
    "daty": "2022-01-15",
    "latitude": -19.715,
    "longitude": 46.75781,
    "type":
		    {
		        "id": 1,
		        "name":"vol"
		    },
	"region":
			{
				"id": 1,
				"province":
				{
					"id": 1,
					"nom":"Antsiranana"
				}
			},
	"statut":
			{
				"id": 1,
				"etat":"Nouveau"
			},
	"signalnew":
			{
				"id": 2,
				"titre":"Accident de voitures su la route de RN1"
			}
}



{
    "description": "Aucun description",
    "daty": "2022-01-15",
    "latitude": -19.715,
    "longitude": 46.75781,
    "type": {
        "id": 3,
        "nom": "autres"
    },
    "region": null,
    "statut": {
        "id": 1,
        "etat": "Nouveau"
    },
    "signalnew": {
        "id": 2,
        "titre": "Accident de voitures su la route de RN1"
    },
    "photoList": []
}


 var tbody=$(".clickb");
                 for(var i=0;i<dataLayer.length;i++)
                    {
                       tbody.
                    }
                let tr=$(".clickb");
                $('div.rotation ul:eq(2)').addClass('image_rotation');
                .children("ul").addClass( "MyClass");



@JsonIdentityInfo(scope = Photo.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")

  @JsonIdentityInfo(scope = Province.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")


  @JsonIdentityInfo(scope = Region.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")


  @JsonIdentityInfo(scope = Signalement.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")


  @JsonIdentityInfo(scope = Signalnew.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")


  @JsonIdentityInfo(scope = Statut.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")

  @JsonIdentityInfo(scope = Type.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")


  @JsonIdentityInfo(scope = Userfront.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")

  @JsonIdentityInfo(scope = Utilisateur.class,
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")