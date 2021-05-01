package com.team32.ong.repository;

import java.io.Serializable;
import com.team32.ong.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("MemberRepository")
public interface MemberRepository extends JpaRepository<Member, Serializable> {
    //query
}
