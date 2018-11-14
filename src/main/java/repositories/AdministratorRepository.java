/*
 * AdministratorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import domain.Actor;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

import java.util.Collection;
import java.util.Map;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    /*	1. The average, the minimum, the maximum, and the standard deviation of the
			number of showrooms per user. */
    @Query(value = "select avg(showrooms.count) " +
            "from (select u.name, count(s.id) count " +
            "from User u left join Showroom s on s.user_id=u.id " +
            "group by u.id) as showrooms", nativeQuery = true)
    Double findAverageShowroomsPerUser();

    @Query(value = "select max(showrooms.count) " +
            "from (select u.name, count(s.id) count " +
            "from User u left join Showroom s on s.user_id=u.id " +
            "group by u.id) as showrooms", nativeQuery = true)
    Integer findMaximunShowroomsPerUser();

    @Query(value = "select min(showrooms.count) " +
            "from (select u.name, count(s.id) count " +
            "from User u left join Showroom s on s.user_id=u.id " +
            "group by u.id) as showrooms", nativeQuery = true)
    Integer findMinimunShowroomsPerUser();

    @Query(value = "select sqrt(sum(showrooms.count*showrooms.count)/count(showrooms.count) - avg(showrooms.count)*avg(showrooms.count))  " +
            "from (select u.name, count(s.id) count " +
            "from User u left join Showroom s on s.user_id=u.id " +
            "group by u.id) as showrooms", nativeQuery = true)
    Double findStdevShowroomsPerUser();

    @Query("select s.user, count(s.user) from Showroom s group by s.user")
    Map<User, Integer> findSCountShowroomsPerUser();

    /*	2. The average, the minimum, the maximum, and the standard deviation of the
			number of items per user. */
    @Query(value = "select avg(items.count)  " +
                    "from (select u.name, count(i.id) count " +
            "from User u left join Showroom s on s.user_id=u.id left join Item i on s.id = i.showroom_id  " +
            "group by u.id) as items", nativeQuery = true)
    Double findAverageItemsPerUser();

    @Query(value = "select max(items.count)  " +
            "from (select u.name, count(i.id) count " +
            "from User u left join Showroom s on s.user_id=u.id left join Item i on s.id = i.showroom_id  " +
            "group by u.id) as items", nativeQuery = true)
    Integer findMaximunItemsPerUser();

    @Query(value = "select min(items.count)  " +
            "from (select u.name, count(i.id) count " +
            "from User u left join Showroom s on s.user_id=u.id left join Item i on s.id = i.showroom_id " +
            "group by u.id) as items", nativeQuery = true)
    Integer findMinimunItemsPerUser();

    @Query(value = "select sqrt(sum(items.count*items.count)/count(items.count) - avg(items.count)*avg(items.count))  " +
            "from (select u.name, count(i.id) count " +
            "from User u left join Showroom s on s.user_id=u.id left join Item i on s.id = i.showroom_id " +
            "group by u.id) as items", nativeQuery = true)
    Double findStdevItemsPerUser();

    @Query("select i.showroom.user, count(i.showroom.user) from Item i group by i.showroom.user")
    Map<User, Integer> findSCountItemsPerUser();

    /*	3. The average, the minimum, the maximum, and the standard deviation of the
			number of requests per user. */
    @Query(value = "select avg(requests.count)  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Request c on c.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Double findAverageRequestsPerUser();
    @Query(value = "select max(requests.count)  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Request c on c.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Integer findMaximunRequestsPerUser();

    @Query(value = "select min(requests.count)  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Request c on c.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Integer findMinimunRequestsPerUser();

    @Query(value = "select sqrt(sum(requests.count*requests.count)/count(requests.count) - avg(requests.count)*avg(requests.count))  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Request c on c.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Double findStdevRequestsPerUser();


    @Query("select r.user, count(r.user) from Request r group by r.user")
    Map<User, Integer> findSCountRequestsPerUser();


    /*	4. The average, the minimum, the maximum, and the standard deviation of the
			number of rejected Requests per user. */
    @Query(value = "select avg(requests.count)  " +
            "from (select u.name, sum(case when r.user_id = u.id and r.status = 'REJECTED' then 1 else 0 end) count " +
            "from User u left join Request r on r.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Double findAverageRejectedRequestsPerUser();

    @Query(value = "select max(requests.count)  " +
            "from (select u.name, sum(case when r.user_id = u.id and r.status = 'REJECTED' then 1 else 0 end) count " +
            "from User u left join Request r on r.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Integer findMaximunRejectedRequestsPerUser();

    @Query(value = "select min(requests.count)  " +
            "from (select u.name, sum(case when r.user_id = u.id and r.status = 'REJECTED' then 1 else 0 end) count " +
            "from User u left join Request r on r.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Integer findMinimunRejectedRequestsPerUser();

    @Query(value = "select sqrt(sum(requests.count*requests.count)/count(requests.count) - avg(requests.count)*avg(requests.count))  " +
            "from (select u.name, sum(case when r.user_id = u.id and r.status = 'REJECTED' then 1 else 0 end) count " +
            "from User u left join Request r on r.user_id=u.id " +
            "group by u.id) as requests", nativeQuery = true)
    Double findStdevRejectedRequestsPerUser();

    @Query("select r.user, count(r.user) from Request r where r.status='REJECTED' group by r.user")
    Map<User, Integer> findSCountRejectedRequestsPerUser();


    /* DashBoard B */
    /*	1. The average, the minimum, the maximum, and the standard deviation of the
           number of chirps per actor. */
    @Query(value = "select avg(chirps.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Chirp c on c.actor_id=u.id " +
            "group by u.id) chirps", nativeQuery = true)
    Double findAverageChirpsPerUser();

    @Query(value = "select max(chirps.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Chirp c on c.actor_id=u.id " +
            "group by u.id) chirps", nativeQuery = true)
    Integer findMaximunChirpsPerUser();

    @Query(value = "select min(chirps.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Chirp c on c.actor_id=u.id " +
            "group by u.id) chirps", nativeQuery = true)
    Integer findMinimunChirpsPerUser();

    @Query(value = "select sqrt(" +
            "sum(chirps.count*chirps.count)/count(chirps.count) - avg(chirps.count)*avg(chirps.count))  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Chirp c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Chirp c on c.actor_id=u.id " +
            "group by u.id) chirps", nativeQuery = true)
    Double findStdevChirpsPerUser();

    @Query("select c.actor, count(c.actor) from Chirp c group by c.actor")
    Map<Actor, Integer> findSCountChirpsPerUser();


    /*	3. The average, the minimum, the maximum, and the standard deviation of the
           number of followeds per actor. */
    @Query("select avg(a.follows.size) from Actor a")
    Double findAverageFollowsPerUser();

    @Query("select max(a.follows.size) from Actor a")
    Integer findMaximunFollowsPerUser();

    @Query("select min(a.follows.size) from Actor a")
    Integer findMinimunFollowsPerUser();

    @Query("select sqrt(sum(a.follows.size*a.follows.size)/count(a.follows.size) - avg(a.follows.size)*avg(a.follows.size))  " +
            "from Actor a")
    Double findStdevFollowsPerUser();

    @Query("select a, a.follows.size from Actor a")
    Map<Actor, Integer> findSCountFollowsPerUser();


    @Query("select c.topic, count(c.id) from Chirp c group by c.topic")
    Collection<Object> findChirpsNumberPerTopic();


    @Query(value = "select avg(comments.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Comment c on c.actor_id=u.id " +
            "group by u.id) as comments ", nativeQuery = true)
    Double findAverageCommentsPerUser();

    @Query(value = "select max(comments.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Comment c on c.actor_id=u.id " +
            "group by u.id) as comments ", nativeQuery = true)
    Integer findMaximunCommentsPerUser();

    @Query(value = "select min(comments.count) " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Comment c on c.actor_id=u.id " +
            "group by u.id) as comments ", nativeQuery = true)
    Integer findMinimunCommentsPerUser();

    @Query(value = "select sqrt(sum(comments.count*comments.count)/count(comments.count) - avg(comments.count)*avg(comments.count))  " +
            "from (select u.name, count(c.id) count " +
            "from User u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Administrator u left join Comment c on c.actor_id=u.id " +
            "group by u.id " +
            "union " +
            "select u.name, count(c.id) count " +
            "from Agent u left join Comment c on c.actor_id=u.id " +
            "group by u.id) as comments ", nativeQuery = true)
    Double findStdevCommentsPerUser();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerUser();

    @Query(value = "select avg(comments.count) " +
            "from (select s.name, count(c.id) count " +
            "from Showroom s left join Comment c on c.commentedObjectId=s.id " +
            "group by s.id) as comments", nativeQuery = true)
    Double findAverageCommentsPerShowroom();

    @Query(value = "select max(comments.count)  " +
            "from (select s.name, count(c.id) count " +
            "from Showroom s left join Comment c on c.commentedObjectId=s.id " +
            "group by s.id) as comments", nativeQuery = true)
    Integer findMaximunCommentsPerShowroom();

    @Query(value = "select min(comments.count)  " +
            "from (select s.name, count(c.id) count " +
            "from Showroom s left join Comment c on c.commentedObjectId=s.id " +
            "group by s.id) as comments", nativeQuery = true)
    Integer findMinimunCommentsPerShowroom();

    @Query(value = "select sqrt(sum(comments.count*comments.count)/count(comments.count) - avg(comments.count)*avg(comments.count))  " +
            "from (select s.name, count(c.id) count " +
            "from Showroom s left join Comment c on c.commentedObjectId=s.id " +
            "group by s.id) as comments", nativeQuery = true)
    Double findStdevCommentsPerShowroom();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerShowroom();

    @Query(value = "select avg(comments.count) " +
            "from (select i.id, count(c.id) count " +
            "from Item i left join Comment c on c.commentedObjectId=i.id " +
            "group by i.id) as comments", nativeQuery = true)
    Double findAverageCommentsPerItem();

    @Query(value = "select max(comments.count) " +
            "from (select i.id, count(c.id) count " +
            "from Item i left join Comment c on c.commentedObjectId=i.id " +
            "group by i.id) as comments", nativeQuery = true)
    Integer findMaximunCommentsPerItem();

    @Query(value = "select min(comments.count) " +
            "from (select i.id, count(c.id) count " +
            "from Item i left join Comment c on c.commentedObjectId=i.id " +
            "group by i.id) as comments", nativeQuery = true)
    Integer findMinimunCommentsPerItem();

    @Query(value = "select sqrt(sum(comments.count*comments.count)/count(comments.count) - avg(comments.count)*avg(comments.count))  " +
            "from (select s.id, count(c.id) count " +
            "from Item s left join Comment c on c.commentedObjectId=s.id " +
            "group by s.id) as comments", nativeQuery = true)
    Double findStdevCommentsPerItem();

    @Query("select c.actor, count(c.id) from Comment c group by c.actor")
    Map<User, Integer> findSCountCommentsPerItem();




}
   

   /* @Query("select t.administrative.id, t.administrative.userAccount.username, count(t.id) from Tender t group by t.administrative.id, t.administrative.name")
    Collection<Object> numberTenderByUser();

    @Query("select sum(case when t.interest = 'UNDEFINED' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'HIGH' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'MEDIUM' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'LOW' then 1 else 0 end)*100/count(t) from Tender t")
    Collection<Object> tendersByInterestLevel();

    @Query("select sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o")
    Collection<Object> offersByState();

    @Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o group by o.commercial.name")
    Collection<Object> offersByStateAndCommercial();

    @Query("select sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o)  from Offer o")
    Collection<Object> offersByStateRatio();

    @Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o) from Offer o group by o.commercial.name")
    Collection<Object> offersByStateAndCommercialRatio();

    @Query("select o "
            + "from Offer o "
            + "where year(o.presentationDate) = year(?1) "
            + "and month(o.presentationDate) = month(?1) "
            + "order by o.amount desc")
    List<Offer> findTopOffersOnMonth(Date date, Pageable pageSize);

    @Query("select o from Offer o "
            + "where o.presentationDate between ?1 and ?2 "
            + "and o.state = 'WINNED' "
            + "order by o.amount desc")
    List<Offer> findTopWinedOffersOnThreeMonths(Date from, Date to, Pageable pageSize);

    @Query("select cr.name, avg(cr.economicalOffer / t.estimatedAmount) from CompanyResult cr join cr.tenderResult tr join tr.tender t where t.estimatedAmount != 0 group by cr.name")
    Collection<Object> findAvgRatioAmounOfferOverTender();

    @Query("select cr.name, count(cr.name) from CompanyResult cr group by cr.name order by count(cr.name) desc")
    List<Object> findTopFrecuentsCompanies(Pageable pageSize);

    @Query("select cr.name, count(cr.name) from CompanyResult cr "
            + "where cr.state = 'WINNER' "
            + "group by cr.name "
            + "order by count(cr.name) desc")
    List<Object> findTopFrecuentsWinnersCompanies(Pageable pageSize);

    @Query("select t from Tender t "
            + "where t.maxPresentationDate between ?1 and ?2 "
            + "and t.interest='HIGH' "
            + "and not exists (select o from Offer o where t=o.tender) "
            + "order by t.estimatedAmount desc")
    List<Tender> findHighInterestNoOferTendersCloseToExpire(Date from, Date to);

    @Query("select t from Tender t "
            + "where t.maxPresentationDate between ?1 and ?2 "
            + "and exists (select o from Offer o where t=o.tender and o.state='ABANDONED') "
            + "order by t.estimatedAmount desc")
    List<Tender> findHighInterestTendersWithAbandonedOfferCloseToExpire(Date from, Date to);

    @Query("select ar "
            + "from AdministrativeRequest ar "
            + "where ar.accepted = false "
            + "order by ar.maxAcceptanceDate desc")
    Collection<AdministrativeRequest> findRejectedAdministrativeRequest();

    @Query("select cr "
            + "from CollaborationRequest cr "
            + "where cr.accepted = false "
            + "order by cr.maxAcceptanceDate desc")
    Collection<CollaborationRequest> findRejectedComercialRequest();

    @Query("select avg(cr.benefitsPercentage), "
            + "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
            + "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
            + "from CollaborationRequest cr "
            + "where cr.accepted = true")
    Collection<Object> findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests();

    @Query("select avg(cr.benefitsPercentage), "
            + "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
            + "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
            + "from CollaborationRequest cr "
            + "where cr.accepted = false")
    Collection<Object> findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests();

*/