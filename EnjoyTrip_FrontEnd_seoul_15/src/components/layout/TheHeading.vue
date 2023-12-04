<script setup></script>

<template>
    <header id="header" style="padding-top: 4%">
        <RouterLink class="logo" :to="{name: 'main'}">
            <img src="../../assets/images/TATI_logo.jpg" alt="logo" style="width: 20%" />
            <strong> (Tourist Attraction Type Indicator)</strong>
        </RouterLink>

        <ul class="icons">
            <li>
                <a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a>
            </li>
            <li>
                <a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a>
            </li>
            <li>
                <a href="#" class="icon fa-snapchat-ghost"><span class="label">Snapchat</span></a>
            </li>
            <li>
                <a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a>
            </li>
            <li>
                <a href="#" class="icon fa-medium"><span class="label">Medium</span></a>
            </li>
        </ul>
    </header>
</template>

<style scoped></style>
