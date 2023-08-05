package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Integer> {
    Jobs getJobById(Integer id) throws Exception;
//    Jobs getJobsByCreatorId(Integer id) throws Exception;
    List<Jobs> findJobsByCreatorId(User creatorId);
    List<Jobs> findJobsByApplicantListContains(User user);

    @Query("SELECT DISTINCT j FROM Jobs j JOIN j.categories c WHERE " +
            "(:illegal IS NULL OR c.illegal = :illegal) " +
            "AND (:mining IS NULL OR c.mining = :mining) " +
            "AND (:combat IS NULL OR c.combat = :combat) " +
            "AND (:salvage IS NULL OR c.salvage = :salvage) " +
            "AND (:trading IS NULL OR c.trading = :trading) " +
            "AND (:exploring IS NULL OR c.exploring = :exploring) " +
            "AND (:bountyHunting IS NULL OR c.bounty_hunting = :bountyHunting) " +
            "AND (:delivery IS NULL OR c.delivery = :delivery) " +
            "AND (:pvp IS NULL OR c.pvp = :pvp) " +
            "AND (:pve IS NULL OR c.pve = :pve) " +
            "AND (:rolePlay IS NULL OR c.rolePlay = :rolePlay)")
    List<Jobs> findJobsByCategoryTags(
            @Param("illegal") Boolean illegal,
            @Param("mining") Boolean mining,
            @Param("combat") Boolean combat,
            @Param("salvage") Boolean salvage,
            @Param("trading") Boolean trading,
            @Param("exploring") Boolean exploring,
            @Param("bountyHunting") Boolean bountyHunting,
            @Param("delivery") Boolean delivery,
            @Param("pvp") Boolean pvp,
            @Param("pve") Boolean pve,
            @Param("rolePlay") Boolean rolePlay
    );
}
