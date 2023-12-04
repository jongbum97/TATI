<script setup></script>

<template>
  <h3>Preformatted</h3>
  <pre><code>i = 0;

while (!deck.isInOrder()) {
    print 'Iteration ' + i;
    deck.shuffle();
    i++;
}

print 'It took ' + i + ' iterations to sort the deck.';
</code></pre>
</template>

<style scoped></style>
