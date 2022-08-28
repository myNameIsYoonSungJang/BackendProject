package com.example.jabdongsani.repository;

import com.example.jabdongsani.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { // <> 안 첫번째 : 관리할대상, 두번째 : 그 데이터의 타입
    // 특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT * " +
            "FROM comment " +
            "WHERE article_id = :articleId", // : 뒤에 적힌 변수명은 findByArticleId로 조회하였던 변수명과 같아야 조회가 가능함.
            nativeQuery = true)

    // 밑의 코드들 2개 모두 @Param 어노테이션을 해주지 않는다면, 코멘트 레포지토리 테스트에서 테스트를 할때,
    // 매개값으로 전달한 articleId 혹은 nickname을 찾지 못할 수도 잇다.
    // 하여 매개값을 전달할때, @Param 어노테이션과 ()안에 해당 매개값의 변수명을 적어주어야 한다.

    List<Comment> findByArticleId(@Param("articleId") Long articleId); // 해당 메소드를 실행하기 위해 위의 쿼리라는 방식을 사용하여 만들수 있다.
    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(@Param("nickname") String nickname); // 해당 메소드를 통해 수행할 sql을 위해 resources -> META-INF -> orm.xml이라는 파일에 작성. (이렇게 만들수도 있다.)
}
