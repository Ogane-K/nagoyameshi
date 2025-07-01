package com.example.nagoyameshi.Controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 管理者用カテゴリ一覧ページ

    @Test
    public void 未ログインの場合はカテゴリ一覧にアクセスできずログイン画面へリダイレクト() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("user2@example.com")
    public void 一般ユーザーとしてアクセスした場合403エラーが出る() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void 管理者としてアクセスした場合カテゴリ一覧が表示される() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories/index"));
    }

    // カテゴリ登録機能

    @Test
    public void 未ログインで登録POSTするとログイン画面へリダイレクト() throws Exception {
        mockMvc.perform(post("/admin/categories/create").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails("user2@example.com")
    public void 一般ユーザーが登録POSTすると403エラー() throws Exception {
        mockMvc.perform(post("/admin/categories/create").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void 管理者が登録POSTすると一覧にリダイレクト() throws Exception {
        mockMvc.perform(post("/admin/categories/create")
                .param("name", "和食").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    // カテゴリ更新機能

    @Test
    public void 未ログインで更新POSTするとログイン画面へ() throws Exception {
        mockMvc.perform(post("/admin/categories/1/update").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails("user2@example.com")
    public void 一般ユーザーが更新POSTすると403エラー() throws Exception {
        mockMvc.perform(post("/admin/categories/1/update").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void 管理者が更新POSTすると一覧にリダイレクト() throws Exception {
        mockMvc.perform(post("/admin/categories/1/update")
                .param("name", "和食").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    // カテゴリ削除機能

    @Test
    public void 未ログインで削除POSTするとログイン画面へ() throws Exception {
        mockMvc.perform(post("/admin/categories/1/delete").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails("user2@example.com")
    public void 一般ユーザーが削除POSTすると403エラー() throws Exception {
        mockMvc.perform(post("/admin/categories/1/delete").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user1@example.com")
    public void 管理者が削除POSTすると一覧にリダイレクト() throws Exception {
        mockMvc.perform(post("/admin/categories/1/delete").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }
}
