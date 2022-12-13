struct Solution;

impl Solution {
    pub fn running_sum(mut nums: Vec<i32>) -> Vec<i32> {
        if nums.len() > 1 {
            (1..nums.len() as usize ).for_each(|i| nums[i] += nums[i-1]);
        }
        nums
    }
}

fn main() {
    println!("{:?}", Solution::running_sum(vec![1,2,3,4]));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn tests() {
        assert_eq!(vec![1,3,6,10], Solution::running_sum(vec![1,2,3,4]));
        assert_eq!(vec![1,2,3,4,5], Solution::running_sum(vec![1,1,1,1,1]));
        assert_eq!(vec![3,4,6,16,17], Solution::running_sum(vec![3,1,2,10,1]));
    }
}
