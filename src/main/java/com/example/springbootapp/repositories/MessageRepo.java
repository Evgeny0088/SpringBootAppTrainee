package com.example.springbootapp.repositories;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query("select new com.example.springbootapp.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " + " " +
            "   count(md), " + " " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ", " +
            "   sum(case when md = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message as m left join m.liked_users as ml " +
            "left join m.disliked_users as md " +
            "group by m")
    Page<MessageDTO> findAll(Pageable pageable, @Param("user") User user);

    @Query("select new com.example.springbootapp.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   count(md), " + " " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ", " +
            "   sum(case when md = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message as m left join m.liked_users as ml " +
            "left join m.disliked_users as md " +
            "where m.tag = :tag " +
            "group by m")
    Page<MessageDTO> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);

    @Query("select new com.example.springbootapp.dto.MessageDTO(" +
            "   m, " +
            "   count(ml), " +
            "   count(md), " + " " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ", " +
            "   sum(case when md = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message as m left join m.liked_users as ml " +
            "left join m.disliked_users as md " +
            "where m.author = :author " +
            "group by m")
    Page<MessageDTO> findByAuthor(@Param("user") User user,@Param("author") User author, Pageable pageable);

}
