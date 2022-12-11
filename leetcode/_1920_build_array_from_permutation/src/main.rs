struct Solution;

impl Solution {
    pub fn build_array(nums: Vec<i32>) -> Vec<i32> {
        let mut res = Vec::with_capacity(nums.len());
        for e in nums.iter() {
            res.push(nums[*e as usize])
        }
        res
    }
}

fn main() {
    println!("{:?}", Solution::build_array(vec![0,2,1,5,3,4]));
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn tests() {
        assert_eq!(vec![0,1,2,4,5,3], Solution::build_array(vec![0,2,1,5,3,4]));
        assert_eq!(vec![4,5,0,1,2,3], Solution::build_array(vec![5,0,1,2,3,4]));
    }
}
