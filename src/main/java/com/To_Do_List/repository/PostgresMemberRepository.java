package com.To_Do_List.repository;

import com.To_Do_List.domain.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgresMemberRepository implements MemberRepository {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    public PostgresMemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int createMember(Member member) {
        int n = sqlSession.insert("Member.mapCreateMember", member);

        if (n > 0) return 1;
        return -1;
    }

    @Override
    public int updateMember(Member member) {
        int n = sqlSession.update("Member.mapUpdateMember", member);

        if (n > 0) return 1;
        return -1;
    }

    @Override
    public Optional<Member> findMemberById(long id) {
        return Optional.ofNullable(sqlSession.selectOne("Member.mapFindMemberById", id));
    }

    @Override
    public Optional<Member> findMemberByNick(String nick) {
        return Optional.ofNullable(sqlSession.selectOne("Member.mapFindMemberByNick", nick));
    }

    @Override
    public List<Member> findAllMembers() {
        return sqlSession.selectList("Member.mapFindAllMembers");
    }
}