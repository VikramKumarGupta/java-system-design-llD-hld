import java.util.*;

public class SlidingWindowTemplate {

    // Maximum sum of subarray of size k
    public static long maxSumSubarray(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length < k) return -1;

        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        long maxSum = windowSum;
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right] - nums[right - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }

    // Longest substring without repeating characters
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen, -1);

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            left = Math.max(left, lastSeen[c] + 1);
            lastSeen[c] = right;
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Longest substring with at most K distinct characters
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) return 0;

        int[] freq = new int[128];
        int distinct = 0;
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (freq[c]++ == 0) distinct++;

            while (distinct > k) {
                char d = s.charAt(left++);
                if (--freq[d] == 0) distinct--;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Minimum window substring
    public static String minWindow(String s, String t) {
        if (s == null || t == null || t.isEmpty() || s.length() < t.length()) return "";

        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int missing = t.length();
        int left = 0;
        int start = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) missing--;
            need[c]--;

            while (missing == 0) {
                int len = right - left + 1;
                if (len < minLen) {
                    minLen = len;
                    start = left;
                }

                char d = s.charAt(left++);
                need[d]++;
                if (need[d] > 0) missing++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    // Find all anagrams in a string
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) return result;

        int[] freq = new int[128];
        for (char c : p.toCharArray()) freq[c]++;

        int left = 0;
        int remaining = p.length();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (freq[c]-- > 0) remaining--;

            if (right - left + 1 > p.length()) {
                char d = s.charAt(left++);
                if (++freq[d] > 0) remaining++;
            }

            if (remaining == 0) result.add(left);
        }
        return result;
    }

    // Permutation in string
    public static boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) return false;

        int[] freq = new int[128];
        for (char c : s1.toCharArray()) freq[c]++;

        int left = 0;
        int remaining = s1.length();

        for (int right = 0; right < s2.length(); right++) {
            char c = s2.charAt(right);
            if (freq[c]-- > 0) remaining--;

            if (right - left + 1 > s1.length()) {
                char d = s2.charAt(left++);
                if (++freq[d] > 0) remaining++;
            }

            if (remaining == 0) return true;
        }
        return false;
    }

    // Maximum number of vowels in a substring
    public static int maxVowels(String s, int k) {
        if (s == null || k <= 0 || s.length() < k) return 0;

        int currentCount = 0;
        for (int i = 0; i < k; i++) {
            if (isVowel(s.charAt(i))) currentCount++;
        }

        int maxCount = currentCount;
        for (int right = k; right < s.length(); right++) {
            if (isVowel(s.charAt(right - k))) currentCount--;
            if (isVowel(s.charAt(right))) currentCount++;
            maxCount = Math.max(maxCount, currentCount);
        }
        return maxCount;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    // Longest repeating character replacement
    public static int characterReplacement(String s, int k) {
        if (s == null || s.isEmpty()) return 0;

        int[] freq = new int[128];
        int left = 0;
        int maxLen = 0;
        int maxCount = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            maxCount = Math.max(maxCount, ++freq[c]);

            while (right - left + 1 - maxCount > k) {
                freq[s.charAt(left++)]--;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Subarray product less than K
    public static long numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || k <= 1) return 0;

        int left = 0;
        long count = 0;
        long product = 1;

        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];

            while (product >= k) {
                product /= nums[left++];
            }
            count += right - left + 1L;
        }
        return count;
    }

    // Fruit into baskets
    public static int totalFruit(int[] fruits) {
        if (fruits == null || fruits.length == 0) return 0;

        Map<Integer, Integer> window = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < fruits.length; right++) {
            window.merge(fruits[right], 1, Integer::sum);

            while (window.size() > 2) {
                int fruit = fruits[left++];
                int count = window.get(fruit) - 1;
                if (count == 0) window.remove(fruit);
                else window.put(fruit, count);
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Common sliding window problems
    // LeetCode 239: Sliding Window Maximum
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) return new int[0];

        int[] result = new int[nums.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();

            dq.offerLast(i);
            if (i >= k - 1) result[index++] = nums[dq.peekFirst()];
        }
        return result;
    }

    // LeetCode 1004: Max Consecutive Ones III
    public static int longestOnes(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0;
        int maxLen = 0;
        int zeros = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;

            while (zeros > k) {
                if (nums[left++] == 0) zeros--;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        // Test maximum sum subarray
        int[] nums = {1, 4, 2, 10, 23, 3, 1, 0, 20};
        int k = 4;
        System.out.println("Max sum of subarray of size " + k + ": " + maxSumSubarray(nums, k));

        // Test longest substring without repeating characters
        String s = "abcabcbb";
        System.out.println("Longest substring without repeating: " + lengthOfLongestSubstring(s));

        // Test minimum window substring
        String s1 = "ADOBECODEBANC";
        String t1 = "ABC";
        System.out.println("Minimum window substring: " + minWindow(s1, t1));

        // Test find anagrams
        String s2 = "cbaebabacd";
        String p = "abc";
        List<Integer> anagrams = findAnagrams(s2, p);
        System.out.println("Anagram indices: " + anagrams);

        // Test permutation in string
        String s3 = "ab";
        String s4 = "eidbaooo";
        System.out.println("Permutation in string: " + (checkInclusion(s3, s4) ? "Yes" : "No"));
    }
}
