<template>
    <section>
        <div class="header">
            By {{users[post.userId].login}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a href="#page=Post" @click="showPost(post.id)">View all</a>
        </div>
    </section>

</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "SidebarPost",
        methods: {
            showPost: function (id) {
                this.$root.$emit("onPost", id);
            }
        }
    }
</script>

<style scoped>

</style>
