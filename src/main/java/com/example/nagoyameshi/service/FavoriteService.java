package com.example.nagoyameshi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.exception.FavoriteNotFoundException;
import com.example.nagoyameshi.repository.FavoriteRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // 指定したidを持つお気に入りを取得
    public Favorite findFavoriteById(Integer id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteNotFoundException("対象のidのお気に入り情報が見つかりません id : " + id));
    }

    // 指定した店舗とユーザーが紐づいたお気に入りを取得
    public Favorite findFavoriteByRestaurantAndUser(Restaurant restaurant, User user) {
        return favoriteRepository.findByRestaurantAndUser(restaurant, user)
                .orElseThrow(() -> new FavoriteNotFoundException(
                        "対象のお気に入り情報が見つかりません 店舗 : " + restaurant.getName() + "ユーザー" + user.getName()));
    }

    // 指定したユーザーのすべてのお気に入りを作成日時が新しい順に並べ替え、ページングされた状態で取得
    public Page<Favorite> findFavoritesByUserOrderByCreatedAtDesc(User user, Pageable pageable) {
        return favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }

    // お気に入りのレコード数を取得
    public Long countFavorites() {
        return favoriteRepository.count();
    }

    // お気に入りの登録
    @Transactional
    public void createFavorite(Restaurant restaurant, User user) {
        Favorite favorite = new Favorite();

        favorite.setRestaurant(restaurant);
        favorite.setUser(user);

        favoriteRepository.save(favorite);
    }

    // お気に入りの解除(削除)
    @Transactional
    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    // 指定したユーザーが指定した店舗をすでにお気に入りに追加済みかどうかをチェック 登録済ならtrue
    public boolean isFavorite(User user,Restaurant restaurant){
        return favoriteRepository.existsByRestaurantAndUser(restaurant, user);
    }

}
