const getTwoDigitValue = (value) => {
  return ("0" + value).substr(-2);
};

/**
 * Returns a custom string for native datetimepicker
 */
export const constructTimePickerDateString = (inputDate) => {
  const currentTime = inputDate || new Date();
  const year = currentTime.getFullYear();
  const month = getTwoDigitValue(currentTime.getMonth() + 1);
  const date = getTwoDigitValue(currentTime.getDate());
  const hour = getTwoDigitValue(currentTime.getHours());
  const minute = getTwoDigitValue(currentTime.getMinutes());

  return `${year}-${month}-${date}T${hour}:${minute}`;
};

/**
 * Returns a custom string for native datetimepicker that is one day from now
 */
export const getOneDayFromNow = () => {
  const date = new Date();
  const oneDayFromNow = new Date(date.getTime() + 86400000);
  return constructTimePickerDateString(oneDayFromNow);
};

export const getDate = (dateString) => {
  return new Date(dateString);
};

/**
 * Returns a string indicating how much time left until dateString
 */
export const getTimeUntil = (dateString) => {
  const endDate = getDate(dateString);
  const currentDate = new Date();
  let diffInTime = endDate - currentDate;
  const daysLeft = Math.floor(diffInTime / (1000 * 60 * 60 * 24));
  const hoursLeft = Math.floor(
    (diffInTime - daysLeft * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
  );
  const minutesLeft = Math.floor(
    (diffInTime -
      daysLeft * (1000 * 60 * 60 * 24) -
      hoursLeft * (1000 * 60 * 60)) /
      (1000 * 60)
  );
  return `${daysLeft} days, ${hoursLeft} hours, ${minutesLeft} minutes left`;
};

export const isExpired = (dateString) => {
  const expirationDate = getDate(dateString);
  const currentDate = new Date();
  return ((expirationDate - currentDate) <= 0)
}
