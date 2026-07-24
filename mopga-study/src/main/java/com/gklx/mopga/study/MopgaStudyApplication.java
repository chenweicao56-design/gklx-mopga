package com.gklx.mopga.study;

import com.gklx.mopga.study.redis.entity.Address;
import com.gklx.mopga.study.redis.entity.Person;
import com.gklx.mopga.study.redis.manager.PeopleRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;

import java.util.Set;


@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.gklx.mopga.study.redis")
public class MopgaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MopgaStudyApplication.class, args);
    }

    @Bean
    CommandLineRunner loadTestData(PeopleRepository repo) {
        return args -> {
            repo.deleteAll();
            // 1. 钢铁侠 - Tony Stark
            String tonySays = "I Am Iron Man. Genius, Billionaire, Playboy, Philanthropist.";
            Address tonysAddress = Address.of("10880", "Malibu Point", "Malibu", "CA", "90265", "USA");
            Person tony = Person.of("Tony", "Stark", 48, tonySays, new Point(-118.783333, 34.033333), tonysAddress,
                    Set.of("intelligence", "money", "suit", "charisma", "arc-reactor"));

// 2. 美国队长 - Steve Rogers
            String steveSays = "I Can Do This All Day. Language!";
            Address stevesAddress = Address.of("569", "Leaman Place", "Brooklyn", "NY", "11201", "USA");
            Person steve = Person.of("Steve", "Rogers", 105, steveSays, new Point(-73.983333, 40.683333), stevesAddress,
                    Set.of("shield", "strength", "leadership", "vibranium", "patriotism"));

// 3. 黑寡妇 - Natasha Romanoff
            String natashaSays = "I've Got Red In My Ledger. I'd Like To Wipe It Out.";
            Address natashasAddress = Address.of("14", "Red Square", "Moscow", "Moscow Oblast", "109012", "Russia");
            Person natasha = Person.of("Natasha", "Romanoff", 34, natashaSays, new Point(37.616667, 55.750000), natashasAddress,
                    Set.of("spycraft", "martial-arts", "widows-bite", "agility", "intelligence"));

// 4. 绿巨人 - Bruce Banner
            String bruceSays = "That's My Secret, Captain: I'm Always Angry.Great";
            Address brucesAddress = Address.of("221B", "Baker Street", "London", "Greater London", "NW1 6XE", "UK");
            Person bruce = Person.of("Bruce", "Banner", 49, bruceSays, new Point(-0.158333, 51.523333), brucesAddress,
                    Set.of("gamma-radiation", "intelligence", "strength", "rage", "phd"));

// 5. 黑豹 - T'Challa
            String tchallaSays = "In My Culture, Death Is Not The End. It's More Of A Stepping Off Point.";
            Address tchallasAddress = Address.of("1", "Golden City", "Wakanda", "East Africa", "WAK-001", "Wakanda");
            Person tchalla = Person.of("T'Challa", "Udaku", 32, tchallaSays, new Point(35.000000, -5.000000), tchallasAddress,
                    Set.of("vibranium-suit", "panther-strength", "king", "agility", "science"));

// 6. 蜘蛛侠 - Peter Parker
            String peterSays = "With Great Power Comes Great Responsibility.";
            Address petersAddress = Address.of("20", "Ingram Street", "Forest Hills", "NY", "11375", "USA");
            Person peter = Person.of("Peter", "Parker", 17, peterSays, new Point(-73.850000, 40.716667), petersAddress,
                    Set.of("web-shooters", "spider-sense", "wall-crawling", "super-strength", "science-nerd"));

// 7. 奇异博士 - Stephen Strange
            String stephenSays = "I Went Forward In Time To View Alternate Futures. To See All The Possible Outcomes Of The Coming Conflict.";
            Address stephensAddress = Address.of("177A", "Bleecker Street", "New York", "NY", "10012", "USA");
            Person stephen = Person.of("Stephen", "Strange", 42, stephenSays, new Point(-74.000000, 40.716667), stephensAddress,
                    Set.of("time-stone", "sling-ring", "magic", "cloning", "neurosurgeon"));

// 8. 星爵 - Peter Quill
            String quillSays = "I'm Not From Earth, I'm From Missouri. That's Not A Planet.";
            Address quillsAddress = Address.of("42", "Milky Way", "Star-Lord", "Knowhere", "G-001", "Andromeda Galaxy");
            Person quill = Person.of("Peter", "Quill", 38, quillSays, new Point(0.000000, 0.000000), quillsAddress,
                    Set.of("walkman", "element-gun", "dance-off", "jet-boots", "half-celestial"));

// 9. 神奇女侠 - Diana Prince
            String dianaSays = "I Used To Want To Save The World. This Is A Lesson In Humility.";
            Address dianasAddress = Address.of("1", "Themyscira", "Paradise Island", "Aegean Sea", "10101", "Greece");
            Person diana = Person.of("Diana", "Prince", 5000, dianaSays, new Point(25.000000, 37.000000), dianasAddress,
                    Set.of("lasso", "bracelets", "tiara", "strength", "agility"));

// 10. 超人 - Clark Kent
            String clarkSays = "I'm Here To Help. I'm A Friend.";
            Address clarkAddress = Address.of("1938", "Sullivan Way", "Metropolis", "DC", "10001", "USA");
            Person clark = Person.of("Clark", "Kent", 36, clarkSays, new Point(-75.000000, 40.000000), clarkAddress,
                    Set.of("flight", "super-strength", "heat-vision", "x-ray-vision", "invulnerability"));

// 11. 蝙蝠侠 - Bruce Wayne
            String bruceWayneSays = "It's Not Who I Am Underneath, But What I Do That Defines Me.";
            Address wayneAddress = Address.of("1007", "Mountain Drive", "Gotham City", "NJ", "07302", "USA");
            Person bruceWayne = Person.of("Bruce", "Wayne", 39, bruceWayneSays, new Point(-74.166667, 40.716667), wayneAddress,
                    Set.of("batarang", "batmobile", "intelligence", "detective", "fear"));

// 12. 洛基 - Loki Laufeyson
            String lokiSays = "I'm Not A God. I'm Just A Very Damn Good Villain.";
            Address lokisAddress = Address.of("Asgard", "Golden Hall", "Asgard", "N/A", "A-001", "Asgard");
            Person loki = Person.of("Loki", "Laufeyson", 1500, lokiSays, new Point(10.000000, 60.000000), lokisAddress,
                    Set.of("magic", "mischief", "illusion", "dagger", "scepter"));

// 13. 死侍 - Wade Wilson
            String wadeSays = "Time To Make The Chimichangas! Maximum Effort!";
            Address wadesAddress = Address.of("123", "Regeneration Avenue", "Vancouver", "BC", "V6Z 2E6", "Canada");
            Person wade = Person.of("Wade", "Wilson", 32, wadeSays, new Point(-123.133333, 49.283333), wadesAddress,
                    Set.of("katanas", "healing-factor", "chimichangas", "fourth-wall", "humor"));

// 14. 神奇先生 - Reed Richards
            String reedSays = "The Only Way To Truly Defeat The Impossible Is To Solve It.";
            Address reedsAddress = Address.of("42", "Yancy Street", "New York", "NY", "10002", "USA");
            Person reed = Person.of("Reed", "Richards", 42, reedSays, new Point(-74.000000, 40.716667), reedsAddress,
                    Set.of("stretch", "intelligence", "inventor", "scientist", "leader"));

// 15. 暴风女 - Ororo Munroe
            String ororoSays = "The Weather Shall Obey My Command. I Am The Wind And The Rain.";
            Address ororosAddress = Address.of("1", "Storm's Peak", "Cairo", "Cairo Governorate", "11511", "Egypt");
            Person ororo = Person.of("Ororo", "Munroe", 34, ororoSays, new Point(31.233333, 30.050000), ororosAddress,
                    Set.of("weather-control", "flight", "lightning", "strength", "leadership"));

// 保存所有数据到repo
            repo.save(tony);
            repo.save(steve);
            repo.save(natasha);
            repo.save(bruce);
            repo.save(tchalla);
            repo.save(peter);
            repo.save(stephen);
            repo.save(quill);
            repo.save(diana);
            repo.save(clark);
            repo.save(bruceWayne);
            repo.save(loki);
            repo.save(wade);
            repo.save(reed);
            repo.save(ororo);
        };
    }

}
